package com.github.bassaer.githubclient.contract

import com.github.bassaer.githubclient.model.GitHubService

/**
 * 各役割がもつコントラクトを定義
 * Created by nakayama on 2017/09/22.
 */
interface RepositoryListContract {

    /**
     * MVPのViewが実装するインターフェース
     * PresenterがViewを操作する時に利用する
     */
    interface View {
        fun getSelectedLanguage(): String
        fun showProgress()
        fun hideProgress()
        fun showRepositories(repositories: GitHubService.Repositories)
        fun showError()
        fun startDetailActivity(fullRepositoryName: String)
    }

    /**
     * MVPのPresenterが実装するインターフェース
     * Viewをクリックした時などにViewがPresenterに教えるために利用する
     */
    interface UserActions {
        fun selectLanguage(language: String)
        fun selectRepositoryItem(item: GitHubService.RepositoryItem)
    }
}