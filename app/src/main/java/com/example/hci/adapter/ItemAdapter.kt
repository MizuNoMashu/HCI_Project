package com.example.affirmations.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.hci.R
import com.example.hci.model.Product

/**
 * Adapter for the [RecyclerView] in [MainActivity]. Displays [Affirmation] data object.
 */
class ItemAdapter(
        private val dataset: MutableList<Product>
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(prod: Product){
            val item_name: TextView = view.findViewById(R.id.item_name)
            val item_image: ImageView = view.findViewById(R.id.item_image)
            val stars: RatingBar = view.findViewById(R.id.stars)
            val count_rating: TextView = view.findViewById(R.id.count_rating)
            val price: TextView = view.findViewById(R.id.price)
            item_name.text = prod.title
            stars.rating = prod.rating
            count_rating.text = prod.review.toString()
            price.text = prod.price.toString()
            item_image.setImageResource(prod.image)
            val bundle = Bundle()

            bundle.putString("title" , prod.title)
            bundle.putFloat("stars", prod.rating)
            bundle.putString("reviews",prod.review.toString())
            bundle.putString("price", prod.price.toString())
            bundle.putInt("image", prod.image)
            bundle.putInt("id_vendor", prod.id)

            this.itemView.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.item_description , bundle)
            )
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItems(dataset[position])
    }



    override fun getItemCount() = dataset.size
}
