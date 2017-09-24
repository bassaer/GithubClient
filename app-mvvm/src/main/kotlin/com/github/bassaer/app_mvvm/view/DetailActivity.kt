package com.github.bassaer.app_mvvm.view

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.github.bassaer.app_mvvm.R
import com.github.bassaer.app_mvvm.contract.DetailViewContract
import com.github.bassaer.app_mvvm.databinding.ActivityDetailBinding
import com.github.bassaer.app_mvvm.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity(), DetailViewContract {
    private val EXTRA_FULL_REPOSITORY_NAME = "EXTRA_FULL_REPOSITORY_NAME"
    private var fullRepositoryName = ""

    override fun getFullRepositoryName() = this.fullRepositoryName

    override fun startBrowser(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun showError(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
                .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityDetailBinding>(
                this, R.layout.activity_detail
        )
        val gitHubService = (application as GitHubClientApplication).gitHubService
        val detailViewModel = DetailViewModel(this, gitHubService)
        binding.viewModel = detailViewModel

        this.fullRepositoryName = intent.getStringExtra(EXTRA_FULL_REPOSITORY_NAME)

        detailViewModel.loadRepositories()
    }

    fun start(context: Context, fullRepositoryName: String) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(EXTRA_FULL_REPOSITORY_NAME, fullRepositoryName)
        context.startActivity(intent)
    }
}
