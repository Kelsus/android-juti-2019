package com.kelsus.juti2019.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelsus.juti2019.model.GiphyImage
import com.kelsus.juti2019.network.ApiRepository
import com.kelsus.juti2019.network.ApiResult
import com.kelsus.juti2019.network.NetworkState
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val repository = ApiRepository()

    val gifLiveData = MutableLiveData<GiphyImage>()
    val errorLiveData = MutableLiveData<String>()
    val networkStateLiveData = MutableLiveData<NetworkState>()

    init {
        getRandomGif("batman")
    }

    fun getRandomGif(tag: String) {
        networkStateLiveData.value = NetworkState.LOADING
        viewModelScope.launch {
            val result = repository.getRandomGif(tag)
            networkStateLiveData.postValue(NetworkState.DONE)
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
