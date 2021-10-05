package com.example.affirmations.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hci.R
import com.example.hci.ldb
import com.example.hci.listener.OnRecyclerViewCart
import com.example.hci.model.Cart
import com.google.android.material.button.MaterialButton

/**
 * Adapter for the [RecyclerView] in [MainActivity]. Displays [Affirmation] data object.
 */
class CartAdapter(private val listener: OnRecyclerViewCart,
                  private val dataset: MutableList<Cart>
) : RecyclerView.Adapter<CartAdapter.ItemViewHolder>() {


    class ItemViewHolder( private val view: View) : RecyclerView.ViewHolder(view) {
        val delete_button: MaterialButton = view.findViewById(R.id.drop_cart)
        val name_cart: TextView = view.findViewById(R.id.name_cart)
        val image_cart: ImageView = view.findViewById(R.id.image_cart)
        val price_cart: TextView = view.findViewById(R.id.price_cart)
        val quantity_cart: TextView = view.findViewById(R.id.quantity_cart)

        fun bindItems(cart: Cart) {


            name_cart.text = cart.ptitle
            price_cart.text = cart.pprice.toString()
            image_cart.setImageResource(cart.pimage)
            quantity_cart.text = cart.quantity.toString()

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_item_view, parent, false)

        return ItemViewHolder( adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItems(dataset[position])
        holder.delete_button.setOnClickListener {
            ldb?.remove_from_cart(
                dataset[position].uid,
                dataset[position].vid,
                dataset[position].ptitle
            )
            dataset.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, dataset.size)
            if(dataset.isEmpty()){
                listener.onRecyclerViewEmpty()
            }
        }


    }


    override fun getItemCount() = dataset.size
}
