package com.github.bassaer.app_mvvm.view

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.bassaer.app_mvvm.R
import com.github.bassaer.app_mvvm.contract.MainViewContract
import com.github.bassaer.app_mvvm.databinding.RepositoryItemBinding
import com.github.bassaer.app_mvvm.viewmodel.RepositoryItemViewModel
import com.github.bassaer.githubclient.model.GitHubService

/**
 * RecyclerViewでリポジトリのリストを表示するためのAdapterクラス
 * このクラスによりRecyclerViewのアイテムのViewの生成し、Viewにデータを入れる
 * Created by nakayama on 2017/09/24.
 */
class RepositoryAdapter(val context: Context, val view: MainViewContract)
    : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    var items: List<GitHubService.RepositoryItem> = ArrayList()

    fun setItemAndRefresh(items: List<GitHubService.RepositoryItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun getItemAt(position: Int) = items[position]

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RepositoryViewHolder {
        val binding = DataBindingUtil.inflate<RepositoryItemBinding>(LayoutInflater.from(context),
                R.layout.repository_item, parent, false)
        binding.viewModel = RepositoryItemViewModel(view)
        return RepositoryViewHolder(binding.root, binding.viewModel)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder?, position: Int) {
        holder?.loadItem(getItemAt(position))
    }

    override fun getItemCount(): Int = items.size

    class RepositoryViewHolder(itemView: View, val viewModel: RepositoryItemViewModel)
        : RecyclerView.ViewHolder(itemView) {

        fun loadItem(item: GitHubService.RepositoryItem) = viewModel.loadItem(item)

    }

}