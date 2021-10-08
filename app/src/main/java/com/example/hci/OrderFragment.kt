package com.example.hci

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.hci.adapter.OrderAdapter
import com.example.hci.adapter.setDivider
import com.example.hci.databinding.FragmentOrderBinding
import com.example.hci.model.Order

class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderBinding.inflate(inflater,container,false)
        val listOrder: MutableList<Order> = ldb!!.getOrder(logged_user!!.id)
        binding.goToShop.setOnClickListener {
            Navigation.findNavController(this.requireView()).navigate(R.id.action_orderFragment_to_scroll_products)
        }
        if(listOrder.size != 0){
            binding.itemOrder.visibility = View.VISIBLE
            binding.goToShop.visibility = View.GONE
            val recyclerView = binding.itemOrder
            recyclerView.setDivider(R.drawable.recycler_view_divider)
            recyclerView.adapter = OrderAdapter( listOrder )
        }
        else{

            binding.itemOrder.visibility = View.GONE
            binding.goToShop.visibility = View.VISIBLE
        }

        return binding.root
    }


}