package com.github.bassaer.app_mvvm.contract

import com.github.bassaer.githubclient.model.GitHubService

/**
 * リポジトリ一覧画面が持っているContract(契約)を定義しておくインターフェース
 * <p>
 * ViewModelが直接Activityを参照しないようにインターフェースで明確に分けている
 * </p>
 * Created by nakayama on 2017/09/23.
 */
interface MainViewContract {
    fun showRepositories(repositories: GitHubService.Repositories)

    fun showError()

    fun startDetailActivity(fullRepositoryName: String)
}