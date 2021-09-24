package com.example.hci

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.affirmations.adapter.ItemAdapter
import com.example.hci.data.ItemData



class ScrollProductsFragment : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = inflater.inflate(R.layout.fragment_scroll_products,container,false)
        val myItems = ItemData().loadItem()
        val recyclerView = binding.findViewById<RecyclerView>(R.id.scroll_products)
        recyclerView.adapter = ItemAdapter( myItems)
        recyclerView.setHasFixedSize(true)
        return binding.rootView
    }


}