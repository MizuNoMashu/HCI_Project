package com.example.hci

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.hci.adapter.ItemAdapter


class ScrollProductsFragment : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if(logged_user == null) NavHostFragment.findNavController(this).navigate(R.id.action_scroll_products_to_loginFragment)
        val binding = inflater.inflate(R.layout.fragment_scroll_products,container,false)


        val recyclerView = binding.findViewById<RecyclerView>(R.id.scroll_products)
        recyclerView.adapter = ItemAdapter( ldb?.select_product()!!)
        recyclerView.setHasFixedSize(true)
        return binding.rootView
    }


}