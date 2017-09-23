package com.github.bassaer.githubclient.view

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.github.bassaer.githubclient.R
import com.github.bassaer.githubclient.model.GitHubService


/**
 *
 * RecyclerViewでリポジトリのリストを表示するためのAdapterクラス
 * このクラスによりRecyclerViewのアイテムのViewの生成し、Viewにデータを入れる
 * Created by nakayama on 2017/09/22.
 */
class RepositoryAdapter(val context: Context,
                        val onRepositoryItemClickListener: OnRepositoryItemClickListener) : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    var items: List<GitHubService.RepositoryItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RepositoryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.repository_item, parent, false)
        return RepositoryViewHolder(view)
    }

    /**
     * RecyclerViewのアイテムのView作成とViewを保持するViewHolderを作成
     */
    override fun getItemCount(): Int = items.size


    /**
     * onCreateViewHolderで作ったViewHolderのViewに
     * setItemsAndRefresh(items)でセットされたデータを入れる
     */
    override fun onBindViewHolder(holder: RepositoryViewHolder?, position: Int) {
        val item = getItemAt(position)

        holder?.itemView?.setOnClickListener {
            onRepositoryItemClickListener.onRepositoryItemClick(item)
        }
        holder?.repositoryName?.text = item.name
        holder?.repositoryDetail?.text = item.description
        holder?.starCount?.text = item.stargazers_count
        val options = RequestOptions().centerCrop()
        GlideApp.with(context)
                .asBitmap()
                .load(item.owner.avatar_url)
                .apply(options)
                .into(object : BitmapImageViewTarget(holder?.repositoryImage) {
                    override fun setResource(resource: Bitmap?) {
                        val circleBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                        circleBitmapDrawable.isCircular = true
                        holder?.repositoryImage?.setImageDrawable(circleBitmapDrawable)
                    }
                })
    }

    /**
     * リポジトリのデータをセットして更新する
     * @param items
     */
    fun setItemAndRefresh(items: List<GitHubService.RepositoryItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun getItemAt(position: Int): GitHubService.RepositoryItem = items[position]


    class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val repositoryName: TextView = itemView.findViewById(R.id.repo_name)
        val repositoryDetail: TextView = itemView.findViewById(R.id.repo_detail)
        val repositoryImage: ImageView = itemView.findViewById(R.id.repo_image)
        val starCount: TextView = itemView.findViewById(R.id.repo_star)
    }

    interface OnRepositoryItemClickListener {
        fun onRepositoryItemClick(item: GitHubService.RepositoryItem)
    }

}