package com.github.bassaer.githubclient.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.github.bassaer.githubclient.R
import com.github.bassaer.githubclient.contract.DetailContract
import com.github.bassaer.githubclient.model.GitHubService
import com.github.bassaer.githubclient.presenter.DetailPresenter

class DetailActivity : AppCompatActivity(), DetailContract.View {
    val EXTRA_FULL_REPOSITORY_NAME = "EXTRA_FULL_REPOSITORY_NAME"
    private lateinit var fullNameTextView: TextView
    private lateinit var detailTextView: TextView
    private lateinit var repositoryStartTextView: TextView
    private lateinit var repositoryForkTextView: TextView
    private lateinit var ownerImage: ImageView
    private lateinit var detailPresenter: DetailContract.UserActions
    private lateinit var fullRepositoryName: String

    fun start(context: Context, fullRepositoryName: String) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(EXTRA_FULL_REPOSITORY_NAME, fullRepositoryName)
        context.startActivity(intent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        this.fullRepositoryName = intent.getStringExtra(EXTRA_FULL_REPOSITORY_NAME)
        this.fullNameTextView = findViewById(R.id.fullname)
        this.detailTextView = findViewById(R.id.detail)
        this.repositoryStartTextView = findViewById(R.id.repo_star)
        this.repositoryForkTextView = findViewById(R.id.repo_fork)
        this.ownerImage = findViewById(R.id.owner_image)

        val gitHubService = (application as GitHubRepositoryApplication).gitHubService
        detailPresenter = DetailPresenter(this, gitHubService)
        detailPresenter.prepare()

    }

    override fun getFullRepositoryName(): String = this.fullRepositoryName

    override fun showRepositoryInfo(response: GitHubService.RepositoryItem) {
        this.fullNameTextView.text = response.full_name
        this.detailTextView.text = response.description
        this.repositoryStartTextView.text = response.stargazers_count
        this.repositoryForkTextView.text = response.forks_count

        GlideApp.with(this)
                .asBitmap()
                .load(response.owner.avatar_url)
                .apply(RequestOptions().centerCrop())
                .into(object : BitmapImageViewTarget(this.ownerImage) {
                    override fun setResource(resource: Bitmap?) {
                        val circularBitmapDrawable
                                = RoundedBitmapDrawableFactory.create(resources, resource)
                        circularBitmapDrawable.isCircular = true
                        ownerImage.setImageDrawable(circularBitmapDrawable)
                    }
                })
        val listener = View.OnClickListener {
            detailPresenter.titleClick()
        }

        fullNameTextView.setOnClickListener(listener)
        ownerImage.setOnClickListener(listener)
    }

    override fun startBrowser(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun showError(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
                .show()
    }
}
