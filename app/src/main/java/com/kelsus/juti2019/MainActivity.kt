package com.kelsus.juti2019

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setContentView(R.layout.activity_main)
        observe()
        getGifButton.setOnClickListener {
            viewModel.getRandomGif(editText.text.toString())
        }
    }

    private fun observe() {
        viewModel.networkStateLiveData.observe(this, Observer {

        })

        viewModel.gifLiveData.observe(this, Observer { giphyImage ->
            GlideApp.with(this).asGif().load(giphyImage.url).into(gifImageView)
        })


        viewModel.errorLiveData.observe(this, Observer {

        })
    }
}
