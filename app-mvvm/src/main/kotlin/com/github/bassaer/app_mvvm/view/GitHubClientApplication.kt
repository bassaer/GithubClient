package com.github.bassaer.app_mvvm.view

import android.app.Application
import android.util.Log
import com.github.bassaer.githubclient.model.GitHubService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Application　拡張
 * Created by nakayama on 2017/09/24.
 */
class GitHubClientApplication: Application() {
    val TAG = "API LOG"
    val GITHUB_URL = "https://api.github.com"

    lateinit var retrofit: Retrofit
    lateinit var gitHubService: GitHubService

    override fun onCreate() {
        super.onCreate()
        setupApiClient()
    }

    private fun setupApiClient() {
        val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            Log.d(TAG, message)
        })

        logging.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder().addInterceptor(logging).build()

        this.retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(GITHUB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        this.gitHubService = this.retrofit.create(GitHubService::class.java)
    }
}