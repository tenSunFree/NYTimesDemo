package com.home.nytimesdemo.list.view

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.home.nytimesdemo.R
import com.home.nytimesdemo.common.AnimationUtil
import com.home.nytimesdemo.list.viewModel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var viewModel: ListViewModel
    private lateinit var adapter: ListAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() {
        adapter = ListAdapter()
        adapter.setOnItemClickListener { article ->
            val bundle = Bundle().apply { putSerializable("image", article) }
            findNavController().navigate(
                R.id.action_articlesFragment_to_articleDetailsFragment,
                bundle
            )
        }
        recycler_view.adapter = adapter
        refresh_layout.setOnRefreshListener {
            viewModel.getListResponse { refresh_layout.isRefreshing = true }
        }
    }

    private fun initViewModel() {
        viewModel = (activity as ListActivity).viewModel
        var lastOnlineStatus = true
        linear_layout_network_status.visibility = View.INVISIBLE
        viewModel.networkLiveData.observe(viewLifecycleOwner, Observer { isConnected ->
            if (lastOnlineStatus != isConnected) {
                lastOnlineStatus = isConnected
                linear_layout_network_status.applyNetworkStatusAnimations(isConnected)
                linear_layout_network_status.applyNetworkStatusVisibilityBehaviour(isConnected)
                refresh_layout.isEnabled = isConnected
            }
        })
        viewModel.responseLiveData.observe(viewLifecycleOwner, Observer {
            refresh_layout.isRefreshing = false
            adapter.differ.submitList(it.list)
            recycler_view.animate().alpha(1f)
        })
    }

    private fun LinearLayout.applyNetworkStatusTheme(isConnected: Boolean) {
        setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                if (isConnected) {
                    R.color.color_53c0f0
                } else {
                    R.color.color_8a140e
                }
            )
        )
        val onlineDrawable =
            ContextCompat.getDrawable(
                requireContext(),
                if (isConnected) {
                    R.drawable.icon_network_on
                } else {
                    R.drawable.icon_network_off
                }
            )
        text_view_network_status.setCompoundDrawablesWithIntrinsicBounds(
            onlineDrawable, null, null, null
        )
        text_view_network_status.text = if (isConnected) {
            "網路已連線。"
        } else {
            "沒有網路連線，請連線網路後重試。"
        }
    }

    private fun LinearLayout.applyNetworkStatusAnimations(isConnected: Boolean) {
        if (!isVisible) {
            AnimationUtil.expand(linear_layout_network_status)
            applyNetworkStatusTheme(isConnected)
        } else {
            AnimationUtil.fadeOutFadeIn(text_view_network_status) {
                applyNetworkStatusTheme(isConnected)
            }
        }
    }

    private fun LinearLayout.applyNetworkStatusVisibilityBehaviour(isConnected: Boolean) {
        lifecycleScope.launch {
            if (isConnected) {
                delay(3000L)
                if (viewModel.networkLiveData.value == true) {
                    AnimationUtil.collapse(this@applyNetworkStatusVisibilityBehaviour)
                }
            }
        }
    }
}