package com.github.bassaer.githubclient.view

import android.app.Application
import android.util.Log
import com.github.bassaer.githubclient.model.GitHubService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Application拡張
 * Created by nakayama on 2017/09/22.
 */
class GitHubRepositoryApplication : Application() {
    val TAG = "API LOG"
    val GITHUB_URL = "https://api.github.com"
    lateinit var retrofit: Retrofit
    lateinit var gitHubService: GitHubService

    override fun onCreate() {
        super.onCreate()
        setupAPIClient()
    }

    private fun setupAPIClient() {
        val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            Log.d(TAG, message)
        })

        logging.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder().addInterceptor(logging).build()

        retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(GITHUB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        gitHubService = retrofit.create(GitHubService::class.java)
    }
}