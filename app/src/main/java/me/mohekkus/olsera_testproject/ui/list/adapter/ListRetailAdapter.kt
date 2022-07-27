package me.mohekkus.olsera_testproject.ui.list.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import me.mohekkus.olsera_testproject.R
import me.mohekkus.olsera_testproject.database.entity.RetailEntity

class ListRetailAdapter(private val allRetail: List<RetailEntity>?) : RecyclerView.Adapter<ListRetailAdapter.ViewHolder>() {

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.component_retail_list, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            allRetail?.get(position)?.apply {
                findViewById<TextView>(R.id.list_title).text = name

                if (inactive)
                    findViewById<TextView>(R.id.list_avail).visibility = View.GONE
                else
                    findViewById<TextView>(R.id.list_status).visibility = View.GONE

                when (position % 2) {
                    0 -> findViewById<LinearLayout>(R.id.listbg).setBackgroundColor(Color.WHITE)
                    else -> findViewById<LinearLayout>(R.id.listbg).setBackgroundColor(R.color.zebra)
                }

                findViewById<ImageView>(R.id.imageView2).load(
                    "https://cdnwpedutorenews.gramedia.net/wp-content/uploads/2022/01/04231721/BLOG_supermarket-retail-physical-store-810x405.jpg"
                )

            }
        }
    }

    override fun getItemCount(): Int = allRetail?.size ?: 0
}