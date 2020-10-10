package com.home.nytimesdemo.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.home.nytimesdemo.R
import com.home.nytimesdemo.list.view.ListActivity
import com.home.nytimesdemo.list.viewModel.ListViewModel

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var viewModel: ListViewModel
    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ListActivity).viewModel
        val bundle = args.image
        Toast.makeText(requireContext(), "$bundle", Toast.LENGTH_LONG).show()
    }
}