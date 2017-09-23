package com.github.bassaer.githubclient.contract

import com.github.bassaer.githubclient.model.GitHubService

/**
 * 各役割がもつコントラクトを定義
 * Created by nakayama on 2017/09/22.
 */
interface DetailContract {

    /**
     * MVPのViewが実装するインターフェース
     * PresenterがViewを操作する時に利用する
     */
    interface View {
        fun getFullRepositoryName(): String
        fun showRepositoryInfo(response: GitHubService.RepositoryItem)
        fun startBrowser(url: String)
        fun showError(message: String)
    }

    /**
     * MVPのPresenterが実装するインターフェース
     * Viewをクリックした時などにViewがPresenterに教えるために利用する
     */
    interface UserActions {
        fun titleClick()
        fun prepare()
    }
}