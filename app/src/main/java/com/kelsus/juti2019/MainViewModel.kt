package com.kelsus.juti2019

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val repository = ApiRepository()

    val gifLiveData = MutableLiveData<GiphyImage>()
    val errorLiveData = MutableLiveData<String>()
    val networkStateLiveData = MutableLiveData<NetworkState>()

    init {
        getRandomGif()
    }

    fun getRandomGif(tag: String? = null) {
        networkStateLiveData.postValue(NetworkState.LOADING)
        viewModelScope.launch {
            networkStateLiveData.postValue(NetworkState.DONE)
            val result = repository.getRandomGif(tag)
            when (result) {
                is ApiResult.Success -> gifLiveData.postValue(result.data)
                is ApiResult.NonSuccess -> {
                    networkStateLiveData.postValue(NetworkState.ERROR)
                    errorLiveData.postValue(result.body)
                }
                is ApiResult.Failure -> {
                    networkStateLiveData.postValue(NetworkState.ERROR)
                    errorLiveData.postValue(result.exception.message)
                }
            }
        }
    }
}
