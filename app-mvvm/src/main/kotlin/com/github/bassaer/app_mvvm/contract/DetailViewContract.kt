package com.github.bassaer.app_mvvm.contract

/**
 * 詳細画面のビューが持っているContract(契約)を定義しておくインターフェース
 * <p>
 * ViewModelが直接Activityを参照しないようにインターフェースで明確に分けている
 * </p>
 * Created by nakayama on 2017/09/23.
 */
interface DetailViewContract {

    fun getFullRepositoryName(): String

    fun startBrowser(url: String)

    fun showError(message: String)
}