package com.home.nytimesdemo.list.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.home.nytimesdemo.common.NetworkManager
import com.home.nytimesdemo.list.model.ListRepository
import com.home.nytimesdemo.list.model.ListResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class ListViewModel(
    application: Application, private val repository: ListRepository
) : AndroidViewModel(application) {

    private val networkManager = NetworkManager(application)
    val networkLiveData = networkManager.connectionStatusLiveData
    private val _responseLiveData = MutableLiveData<ListResponse>()
    val responseLiveData: LiveData<ListResponse>
        get() = _responseLiveData

    init {
        getListResponse()
    }

    fun getListResponse(refreshFailed: () -> Unit = {}) {
        if (networkLiveData.value == true) {
            viewModelScope.launch(IO) {
                supervisorScope {
                    try {
                        val ff = repository.getListResponse()
                        _responseLiveData.postValue(ff)
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }
                }
            }
        } else {
            refreshFailed.invoke()
        }
    }
}