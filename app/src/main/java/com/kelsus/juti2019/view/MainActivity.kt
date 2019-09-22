package com.kelsus.juti2019.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kelsus.juti2019.R
import com.kelsus.juti2019.network.NetworkState
import com.kelsus.juti2019.utils.loadGif
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
    }

    private fun reloadGif() {
        viewModel.getRandomGif(editText.text.toString())
    }

    private fun showError(message: String) {
        errorTextView.text = message
    }

    private fun loadingState() {
        getGifButton.isEnabled = false
        progressBar.visibility = View.VISIBLE
        gifImageView.visibility = View.INVISIBLE
        errorTextView.visibility = View.GONE
    }

    private fun doneState() {
        getGifButton.isEnabled = true
        progressBar.visibility = View.GONE
        gifImageView.visibility = View.VISIBLE
        errorTextView.visibility = View.GONE
    }

    private fun errorState() {
        getGifButton.isEnabled = true
        progressBar.visibility = View.GONE
        errorTextView.visibility = View.VISIBLE
        gifImageView.setImageBitmap(null)
    }

    private fun loadGif(url: String) {
        loadingState()
        gifImageView.loadGif(url,
            onError = {
                errorState()
                showError(getString(R.string.error_loading_gif))
            },
            onDone = {
                doneState()
            })
    }

    private fun observe() {
        viewModel.networkStateLiveData.observe(this, Observer { networkState ->
            networkState?.let {
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
