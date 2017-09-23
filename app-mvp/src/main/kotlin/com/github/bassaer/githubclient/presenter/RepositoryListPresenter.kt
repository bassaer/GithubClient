package com.github.bassaer.githubclient.presenter

import android.text.format.DateFormat
import com.github.bassaer.githubclient.contract.RepositoryListContract
import com.github.bassaer.githubclient.model.GitHubService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


/**
 * Presenter
 * Created by nakayama on 2017/09/22.
 */
class RepositoryListPresenter(val repositoryListView: RepositoryListContract.View,
                              val gitHubService: GitHubService): RepositoryListContract.UserActions {
    override fun selectLanguage(language: String) {
        loadRepositories()
    }

    override fun selectRepositoryItem(item: GitHubService.RepositoryItem) {
        repositoryListView.startDetailActivity(item.full_name)
    }

    private fun loadRepositories() {
        repositoryListView.showProgress()

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -7)
        val dateText = DateFormat.format("yyyy-MM-dd", calendar).toString()
        val builder = StringBuilder()
        builder.append("language:")
                .append(repositoryListView.getSelectedLanguage())
                .append(" ")
                .append("created:>")
                .append(dateText)

        val observable: Observable<GitHubService.Repositories>
                = gitHubService.listRepository(builder.toString())
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ repositories ->
                    repositoryListView.hideProgress()
                    repositoryListView.showRepositories(repositories)
                }, {
                    repositoryListView.showError()
                })
    }
}