package me.mohekkus.olsera_testproject.ui.list.adapter

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import me.mohekkus.olsera_testproject.R
import me.mohekkus.olsera_testproject.database.entity.RetailEntity
import me.mohekkus.olsera_testproject.databinding.ComponentRetailListBinding
import me.mohekkus.olsera_testproject.ui.list.RetailViewModel
import javax.inject.Inject

class PagingAdapter @Inject constructor() : PagingDataAdapter<RetailEntity, PagingAdapter.ViewHolder>(PagingCallback) {

    inner class ViewHolder(private val binding: ComponentRetailListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RetailEntity) = with(binding) {
            this.datas = item
            when (item.inactive) {
                true -> listAvail.visibility = View.GONE
                else -> listStatus.visibility = View.GONE
            }

            imageView2.load(
                "https://cdnwpedutorenews.gramedia.net/wp-content/uploads/2022/01/04231721/BLOG_supermarket-retail-physical-store-810x405.jpg"
            )
        }
    }

    object PagingCallback : DiffUtil.ItemCallback<RetailEntity>() {
        override fun areItemsTheSame(oldItem: RetailEntity, newItem: RetailEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: RetailEntity, newItem: RetailEntity) =
            oldItem == newItem
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }

        Log.e("getItem(position)", getItem(position)?.name.toString())

        holder.itemView.apply {
            when (position % 2) {
                0 -> findViewById<LinearLayout>(R.id.listbg).setBackgroundColor(Color.WHITE)
                else -> findViewById<LinearLayout>(R.id.listbg).setBackgroundColor(R.color.zebra)
            }

            setOnClickListener {
                val bundle = Bundle().apply {
                    putBoolean("edit", true)
                    putSerializable("data", getItem(position))
                }
                findNavController().navigate(R.id.formFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ComponentRetailListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

}