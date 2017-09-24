package com.github.bassaer.app_mvvm.viewmodel

import android.databinding.ObservableField
import android.view.View
import com.github.bassaer.app_mvvm.contract.DetailViewContract
import com.github.bassaer.githubclient.model.GitHubService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * ViewModel
 * Created by nakayama on 2017/09/24.
 */
class DetailViewModel(val detailView: DetailViewContract,
                      val gitHubService: GitHubService) {

    val repositoryFullName = ObservableField<String>()
    val repositoryDetail = ObservableField<String>()
    val repositoryStar = ObservableField<String>()
    val repositoryFork = ObservableField<String>()
    val repositoryImageUrl = ObservableField<String>()

    private lateinit var repositoryItem: GitHubService.RepositoryItem

    fun loadRepositories() {
        val fullRepositoryName = this.detailView.getFullRepositoryName()
        val repositoryData = fullRepositoryName.split("/")
        val owner = repositoryData[0]
        val repositoryName = repositoryData[1]

        this.gitHubService.detailRepository(owner, repositoryName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { repositoryItem -> loadRepositoryItem(repositoryItem) },
                        { detailView.showError("Failed to read") }
                )
    }

    private fun loadRepositoryItem(item: GitHubService.RepositoryItem) {
        this.repositoryItem = item
        this.repositoryFullName.set(item.full_name)
        this.repositoryDetail.set(item.description)
        this.repositoryStar.set(item.stargazers_count)
        this.repositoryFork.set(item.forks_count)
        this.repositoryImageUrl.set(item.owner.avatar_url)
    }

    fun onTitleClick(view: View) {
        try {
            detailView.startBrowser(this.repositoryItem.html_url)
        } catch (e: Exception) {
            detailView.showError("Failed to open")
        }
    }

}