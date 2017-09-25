package com.github.bassaer.app_mvvm.viewmodel

import android.databinding.ObservableField
import android.view.View
import com.github.bassaer.app_mvvm.contract.MainViewContract
import com.github.bassaer.githubclient.model.GitHubService

/**
 * ViewModel
 * Created by nakayama on 2017/09/23.
 */
class RepositoryItemViewModel(val view: MainViewContract) {

    val repositoryName = ObservableField<String>()
    val repositoryDetail = ObservableField<String>()
    val repositoryStar = ObservableField<String>()
    val repositoryImageUrl = ObservableField<String>()

    lateinit var fullName: String

    fun loadItem(item: GitHubService.RepositoryItem) {
        this.fullName = item.full_name
        repositoryDetail.set(item.description)
        repositoryName.set(item.name)
        repositoryStar.set(item.stargazers_count)
        repositoryImageUrl.set(item.owner.avatar_url)
    }

    fun onItemClick(itemView: View) {
        this.view.startDetailActivity(this.fullName)
    }
}

