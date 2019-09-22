package com.kelsus.juti2019.utils

import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

fun ImageView.loadGif(url: String, onError: () -> Unit, onDone: () -> Unit) {
    val listener = object : RequestListener<GifDrawable> {
        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<GifDrawable>?, isFirstResource: Boolean): Boolean {
            onError.invoke()
            return true
        }

        override fun onResourceReady(resource: GifDrawable?, model: Any?, target: Target<GifDrawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            onDone.invoke()
            return false
        }
    }
    GlideApp.with(this).asGif().load(url)
        .addListener(listener).into(this)
}