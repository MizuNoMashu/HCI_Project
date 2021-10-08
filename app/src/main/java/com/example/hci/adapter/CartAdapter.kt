package com.example.hci.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.hci.R
import com.example.hci.ldb
import com.example.hci.listener.OnRecyclerViewCart
import com.example.hci.logged_user
import com.example.hci.model.Cart
import com.google.android.material.button.MaterialButton

/**
 * Adapter for the [RecyclerView] in [MainActivity]. Displays [Affirmation] data object.
 */
class CartAdapter(
    private val listener: OnRecyclerViewCart
) : RecyclerView.Adapter<CartAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val delete_button: MaterialButton = view.findViewById(R.id.drop_cart)
        val name_cart: TextView = view.findViewById(R.id.name_cart)
        val image_cart: ImageView = view.findViewById(R.id.image_cart)
        val price_cart: TextView = view.findViewById(R.id.price_cart)
        val quantity_cart: TextView = view.findViewById(R.id.quantity_cart)
        val add_quantity: CardView = view.findViewById(R.id.more)
        val remove_quantity: CardView = view.findViewById(R.id.less)

        fun bindItems(cart: Cart) {
            name_cart.text = cart.ptitle
            price_cart.text = cart.pprice.toString()
            image_cart.setImageResource(cart.pimage)
            quantity_cart.text = cart.quantity.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_item_view, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val dataset: MutableList<Cart> = ldb!!.select_from_cart(logged_user!!.id)
        listener.totalPrizeCount(logged_user!!.id)
        holder.bindItems(dataset[position])


        holder.add_quantity.setOnClickListener{
            var add_quantity_check = ldb?.adjustQuantity(dataset[position].uid,dataset[position].vid,dataset[position].ptitle,"add")
            holder.quantity_cart.text = add_quantity_check.toString()
            listener.totalPrizeCount(logged_user!!.id)
        }
        holder.remove_quantity.setOnClickListener{
            val remove_quantity_check = ldb?.adjustQuantity(dataset[position].uid,dataset[position].vid,dataset[position].ptitle,"remove")
            if(remove_quantity_check!! <= 0){

                ldb?.remove_from_cart(
                    dataset[position].uid,
                    dataset[position].vid,
                    dataset[position].ptitle

                )
                dataset.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, itemCount)
                if (dataset.isEmpty()) {
                    listener.onRecyclerViewEmpty()
                }
            }
            else{
                listener.totalPrizeCount(logged_user!!.id)
                holder.quantity_cart.text = remove_quantity_check.toString()
            }
        }
        holder.delete_button.setOnClickListener {
            ldb?.remove_from_cart(
                dataset[position].uid,
                dataset[position].vid,
                dataset[position].ptitle
            )
            dataset.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,itemCount)
            if (dataset.isEmpty()) {
                listener.onRecyclerViewEmpty()
            }
        }
    }

    override fun getItemCount(): Int {
        return ldb!!.select_from_cart(logged_user!!.id).size
    }

}