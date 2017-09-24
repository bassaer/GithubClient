package com.github.bassaer.app_mvvm.view

import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.widget.ImageView
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.github.bassaer.githubclient.view.GlideApp

/**
 * 画像周り
 * Created by nakayama on 2017/09/24.
 */
object BindingAdapters {
    @JvmStatic
    @BindingAdapter("app:imageUrl")
    fun loadImage(imageView: ImageView, imageUrl: String?) {
        GlideApp.with(imageView.context)
                .asBitmap()
                .load(imageUrl)
                .centerCrop()
                .into(object : BitmapImageViewTarget(imageView) {
                    override fun setResource(resource: Bitmap?) {
                        val circleBitmapDrawable
                                = RoundedBitmapDrawableFactory.create(imageView.resources, resource)
                        circleBitmapDrawable.isCircular = true
                        imageView.setImageDrawable(circleBitmapDrawable)
                    }
                })

    }
}