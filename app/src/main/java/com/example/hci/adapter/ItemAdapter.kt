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
import com.example.hci.model.Item

/**
 * Adapter for the [RecyclerView] in [MainActivity]. Displays [Affirmation] data object.
 */
class ItemAdapter(
        private val dataset: List<Item>
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just an Affirmation object.
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val item_name: TextView = view.findViewById(R.id.item_name)
        val item_image: ImageView = view.findViewById(R.id.item_image)
        val stars: RatingBar = view.findViewById(R.id.stars)
        val count_rating: TextView = view.findViewById(R.id.count_rating)
        val price: TextView = view.findViewById(R.id.price)
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.item_name.text = item.title
        holder.stars.rating = item.rating
        holder.count_rating.text = item.reviews.toString()
        holder.price.text = item.price.toString()
        holder.item_image.setImageResource(item.image)
        val bundle = Bundle()

        bundle.putString("title" , item.title)
        bundle.putFloat("stars", item.rating)
        bundle.putString("reviews",item.reviews.toString())
        bundle.putString("price", item.price.toString())
        bundle.putInt("image", item.image)

        holder.itemView.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.item_description , bundle)
        )
    }


    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = dataset.size
}
