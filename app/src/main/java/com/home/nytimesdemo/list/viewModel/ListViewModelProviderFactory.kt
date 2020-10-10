package com.home.nytimesdemo.list.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.home.nytimesdemo.list.model.ListRepository

class ListViewModelProviderFactory(
    private val application: Application, private val repository: ListRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListViewModel(application, repository) as T
    }
}