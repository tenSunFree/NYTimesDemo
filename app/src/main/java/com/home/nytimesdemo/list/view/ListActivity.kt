package com.home.nytimesdemo.list.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.home.nytimesdemo.R
import com.home.nytimesdemo.list.model.ListRepository
import com.home.nytimesdemo.list.viewModel.ListViewModel
import com.home.nytimesdemo.list.viewModel.ListViewModelProviderFactory

class ListActivity : AppCompatActivity() {

    lateinit var viewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        initViewModel()
    }

    private fun initViewModel() {
        val repository = ListRepository()
        val viewModelProviderFactory = ListViewModelProviderFactory(this.application, repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(ListViewModel::class.java)
    }
}