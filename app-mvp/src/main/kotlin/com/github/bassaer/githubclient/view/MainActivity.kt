package com.github.bassaer.githubclient.view

import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import com.github.bassaer.githubclient.R
import com.github.bassaer.githubclient.contract.RepositoryListContract
import com.github.bassaer.githubclient.model.GitHubService
import com.github.bassaer.githubclient.presenter.RepositoryListPresenter

class MainActivity : AppCompatActivity(),
        RepositoryAdapter.OnRepositoryItemClickListener,
        RepositoryListContract.View {

    private lateinit var languageSpinner: Spinner
    private lateinit var progressBar: ProgressBar
    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var repositoryAdapter: RepositoryAdapter
    private lateinit var repositoryListPresenter: RepositoryListContract.UserActions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
        val gitHubService = (application as GitHubRepositoryApplication).gitHubService
        repositoryListPresenter = RepositoryListPresenter(this, gitHubService)
    }

    private fun setupViews() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_repos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        this.repositoryAdapter = RepositoryAdapter(this, this)
        recyclerView.adapter = this.repositoryAdapter


        this.progressBar = findViewById(R.id.progress_bar)
        this.coordinatorLayout = findViewById(R.id.coordinator_layout)
        this.languageSpinner = findViewById(R.id.language_spinner)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        adapter.addAll("java", "objective-c", "swift", "groovy", "python", "ruby", "c")
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        languageSpinner.adapter = adapter
        languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val language = languageSpinner.getItemAtPosition(position) as String
                repositoryListPresenter.selectLanguage(language)
            }
        }
    }


    override fun getSelectedLanguage(): String = languageSpinner.selectedItem as String

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun showRepositories(repositories: GitHubService.Repositories) {
        repositoryAdapter.setItemAndRefresh(repositories.items)
    }

    override fun showError() {
        Snackbar.make(coordinatorLayout, getString(R.string.error_read), Snackbar.LENGTH_SHORT)
                .setAction("Action", null)
                .show()
    }

    override fun startDetailActivity(fullRepositoryName: String) {
        DetailActivity().start(this, fullRepositoryName)
    }

    override fun onRepositoryItemClick(item: GitHubService.RepositoryItem) {
        repositoryListPresenter.selectRepositoryItem(item)
    }


}
