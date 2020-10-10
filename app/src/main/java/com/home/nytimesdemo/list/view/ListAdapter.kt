package com.home.nytimesdemo.list.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.home.nytimesdemo.R
import com.home.nytimesdemo.list.model.ListResponseImage
import kotlinx.android.synthetic.main.item_list.view.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private val differCallback =
        object : DiffUtil.ItemCallback<ListResponseImage>() {
            override fun areItemsTheSame(
                oldItem: ListResponseImage, newItem: ListResponseImage
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListResponseImage, newItem: ListResponseImage
            ): Boolean {
                return oldItem == newItem
            }
        }
    val differ = AsyncListDiffer(this, differCallback)
    private var onItemClickListener: ((ListResponseImage) -> Unit)? = null

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = differ.currentList[position]
        holder.itemView.apply {
            text_view.text = image.name
            image_view.load(image.url) {
                crossfade(true)
                crossfade(200)
                transformations(
                    RoundedCornersTransformation(
                        16f, 16f, 16f, 16f
                    )
                )
            }
            holder.itemView.setOnClickListener { onItemClickListener?.let { it(image) } }
        }
    }

    fun setOnItemClickListener(listener: (ListResponseImage) -> Unit) {
        onItemClickListener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}