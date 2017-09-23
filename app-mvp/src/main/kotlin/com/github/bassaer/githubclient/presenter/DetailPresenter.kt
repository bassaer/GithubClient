package com.github.bassaer.githubclient.presenter

import com.github.bassaer.githubclient.contract.DetailContract
import com.github.bassaer.githubclient.model.GitHubService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by nakayama on 2017/09/22.
 */
class DetailPresenter(val detailView: DetailContract.View,
                      val gitHubService: GitHubService) : DetailContract.UserActions {

    lateinit var repositoryItem: GitHubService.RepositoryItem

    override fun titleClick() {
        try {
            detailView.startBrowser(repositoryItem.html_url)
        } catch (e: Exception) {
            detailView.showError(e.localizedMessage)
        }
    }

    override fun prepare() {
        loadRepositories()
    }

    private fun loadRepositories() {
        val fullRepositoryName = detailView.getFullRepositoryName()
        val repositoryData = fullRepositoryName.split("/")
        val owner = repositoryData[0]
        val repositoryName = repositoryData[1]

        gitHubService
                .detailRepository(owner, repositoryName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    repositoryItem = response
                    detailView.showRepositoryInfo(response)
                }, {
                    detailView.showError("Failed to Read")
                })
    }
}