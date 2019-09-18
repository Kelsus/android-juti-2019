package com.kelsus.juti2019.view

import android.os.Bundle
import android.view.KeyEvent
import android.view.View.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.kelsus.juti2019.R
import com.kelsus.juti2019.network.NetworkState
import com.kelsus.juti2019.utils.GlideApp
import com.kelsus.juti2019.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setContentView(R.layout.activity_main)
        observe()
        getGifButton.setOnClickListener {
            reloadGif()
        }

        editText.setOnKeyListener { view, keyCode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                reloadGif()
            }
            true
        }
    }

    private fun reloadGif() {
        viewModel.getRandomGif(editText.text.toString())
    }

    private fun showError(message: String) {
        errorTextView.text = message
    }

    private fun loadingState() {
        getGifButton.isEnabled = false
        progressBar.visibility = VISIBLE
        gifImageView.visibility = INVISIBLE
        errorTextView.visibility = GONE
    }

    private fun doneState() {
        getGifButton.isEnabled = true
        progressBar.visibility = GONE
        gifImageView.visibility = VISIBLE
        errorTextView.visibility = GONE
    }

    private fun errorState() {
        getGifButton.isEnabled = true
        progressBar.visibility = GONE
        gifImageView.visibility = VISIBLE
        errorTextView.visibility = VISIBLE
        gifImageView.setImageBitmap(null)
    }

    private fun loadGif(url: String) {
        loadingState()
        val listener = object : RequestListener<GifDrawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<GifDrawable>?, isFirstResource: Boolean): Boolean {
                errorState()
                showError(getString(R.string.error_loading_gif))
                return true
            }

            override fun onResourceReady(resource: GifDrawable?, model: Any?, target: Target<GifDrawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                doneState()
                return false
            }
        }

        GlideApp.with(this).asGif().load(url)
                .addListener(listener).into(gifImageView)
    }

    private fun observe() {
        viewModel.networkStateLiveData.observe(this, Observer { networkState ->
            when (networkState) {
                NetworkState.LOADING -> {
                    loadingState()
                }
                NetworkState.DONE -> {
                    doneState()
                }
                NetworkState.ERROR -> {
                    errorState()
                }
            }
        })

        viewModel.gifLiveData.observe(this, Observer { giphyImage ->
            giphyImage?.url?.let { url ->
                loadGif(url)
            }
        })

        viewModel.errorLiveData.observe(this, Observer {
            showError(it)
        })
    }
}
