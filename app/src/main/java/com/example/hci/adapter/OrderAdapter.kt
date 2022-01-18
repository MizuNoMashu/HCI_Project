package com.example.hci.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.hci.R
import com.example.hci.model.Order

class OrderAdapter ( private val dataset: MutableList<Order>
    ) : RecyclerView.Adapter<OrderAdapter.ItemViewHolder>() {

        class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
            fun bindItems(order: Order){
                val item_name: TextView = view.findViewById(R.id.name_order)
                val item_image: ImageView = view.findViewById(R.id.image_order)
                val quantity: TextView = view.findViewById(R.id.quantity)
                val time: TextView = view.findViewById(R.id.ordertime)

                time.text = "added: "+order.time
                item_name.text = order.ptitle
                item_image.setImageResource(order.pimage)
                quantity.text = order.pquantity.toString()
            }
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            // create a new view
            val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.order_item_view, parent, false)

            return ItemViewHolder(adapterLayout)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            holder.bindItems(dataset[position])
        }



        override fun getItemCount() = dataset.size
    }

fun RecyclerView.setDivider(@DrawableRes drawableRes: Int) {
    val divider = DividerItemDecoration(
        this.context,
        DividerItemDecoration.VERTICAL
    )
    val drawable = ContextCompat.getDrawable(
        this.context,
        drawableRes
    )
    drawable?.let {
        divider.setDrawable(it)
        addItemDecoration(divider)
    }
}

