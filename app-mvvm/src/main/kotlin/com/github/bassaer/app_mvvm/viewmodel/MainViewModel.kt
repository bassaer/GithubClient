package com.github.bassaer.app_mvvm.viewmodel

import android.databinding.ObservableInt
import android.text.format.DateFormat
import android.view.View
import android.widget.AdapterView
import com.github.bassaer.app_mvvm.contract.MainViewContract
import com.github.bassaer.githubclient.model.GitHubService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * ViewModel
 * Created by nakayama on 2017/09/24.
 */
class MainViewModel(val repositoryListView: MainViewContract,
                    val gitHubService: GitHubService) {
    val progressBarVisibility = ObservableInt(View.VISIBLE)

    fun onLanguageSpinnerItemSelected(parent: AdapterView<*>,view: View, position: Int, id: Long) {
        loadRepositories(parent.getItemAtPosition(position) as String)
    }

    private fun loadRepositories(language: String) {
        progressBarVisibility.set(View.VISIBLE)

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -7)
        val dateText = DateFormat.format("yyyy-MM-dd", calendar).toString()

        val query = StringBuilder()
                .append("language:")
                .append(language)
                .append(" created:>")
                .append(dateText)
                .toString()

        gitHubService.listRepository(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( { repositories ->
                    progressBarVisibility.set(View.GONE)
                    repositoryListView.showRepositories(repositories)
                }, {
                    repositoryListView.showError()
                })

    }
}