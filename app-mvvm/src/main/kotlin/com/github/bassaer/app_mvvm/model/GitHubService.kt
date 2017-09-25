package com.github.bassaer.githubclient.model

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * RetrofitでGithubのAPIを利用するためのクラス
 * Created by nakayama on 2017/09/22.
 */
interface GitHubService {

    @GET("search/repositories?sort=stars&order=desc")
    fun listRepository(@Query("q") query: String): Observable<Repositories>

    @GET("repos/{repoOwner}/{repoName}")
    fun detailRepository(@Path(value = "repoOwner") owner: String,
                         @Path(value = "repoName") repoName: String): Observable<RepositoryItem>

    class Repositories(val items: List<RepositoryItem>)

    class RepositoryItem(val description: String,
                                val owner: Owner,
                                val language: String,
                                val name: String,
                                val stargazers_count: String,
                                val forks_count: String,
                                val full_name: String,
                                val html_url: String)

    class Owner(receivedEventsUrl: String,
                       orgUrl: String,
                       val avatar_url: String,
                       gravatarId: String,
                       siteAdmin: String,
                       type: String,
                       id: String,
                       htmlUrl: String,
                       followingUrl: String,
                       eventsUrl: String,
                       login: String,
                       subscriptionsUrl: String,
                       repositoryUrl: String)
}