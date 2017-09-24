package com.github.bassaer.app_mvvm.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.github.bassaer.app_mvvm.R
import com.github.bassaer.app_mvvm.contract.MainViewContract
import com.github.bassaer.app_mvvm.databinding.ActivityMainBinding
import com.github.bassaer.app_mvvm.viewmodel.MainViewModel
import com.github.bassaer.githubclient.model.GitHubService

class MainActivity : AppCompatActivity(), MainViewContract {

    lateinit var repositoryAdapter: RepositoryAdapter
    lateinit var coordinatorLayout: CoordinatorLayout

    override fun showRepositories(repositories: GitHubService.Repositories) {
        this.repositoryAdapter.setItemAndRefresh(repositories.items)
    }

    override fun showError() {
        Snackbar.make(coordinatorLayout, getString(R.string.error_read), Snackbar.LENGTH_SHORT)
                .setAction("Action", null)
                .show()
    }

    override fun startDetailActivity(fullRepositoryName: String) {
        DetailActivity().start(this, fullRepositoryName)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
                this, R.layout.activity_main
        )
        val gitHubService = (application as GitHubClientApplication).gitHubService
        binding.viewModel = MainViewModel(this, gitHubService)
        setupViews()
    }

    private fun setupViews() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_repos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        this.repositoryAdapter = RepositoryAdapter(this, this)
        recyclerView.adapter = repositoryAdapter
        coordinatorLayout = findViewById(R.id.coordinator_layout)

        val languageSpinner = findViewById<Spinner>(R.id.language_spinner)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        adapter.addAll("java", "objective-c", "swift", "groovy", "python", "ruby", "c")
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        languageSpinner.adapter = adapter
    }
}
