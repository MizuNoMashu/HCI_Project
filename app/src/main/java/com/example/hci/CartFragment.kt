package com.example.hci

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.hci.adapter.CartAdapter
import com.example.hci.databinding.FragmentCartBinding
import com.example.hci.listener.OnRecyclerViewCart
import com.example.hci.model.Cart


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class CartFragment : Fragment() , OnRecyclerViewCart {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater,container,false)
        val listCart: MutableList<Cart> = ldb!!.select_from_cart(logged_user!!.id)
        binding.goToShop.setOnClickListener {
            Navigation.findNavController(this.requireView()).navigate(R.id.action_cartFragment_to_scroll_products)
        }
        if(listCart.size != 0){
            binding.orderLayout.visibility = View.VISIBLE
            binding.itemCart.visibility = View.VISIBLE
            binding.goToShop.visibility = View.GONE
            val recyclerView = binding.itemCart
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = CartAdapter( this )
        }
        else{
            binding.orderLayout.visibility = View.GONE
            binding.itemCart.visibility = View.GONE
            binding.goToShop.visibility = View.VISIBLE
        }
        binding.orderButton.setOnClickListener{
            ldb?.insertOrder(logged_user!!.id)
            binding.itemCart.visibility = View.GONE
            binding.orderLayout.visibility = View.GONE
            binding.goToShop.visibility = View.VISIBLE
            Toast.makeText( activity, "Order added to Orders list", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    override fun onRecyclerViewEmpty() {
        binding.orderLayout.visibility = View.GONE
        binding.itemCart.visibility = View.GONE
        binding.goToShop.visibility = View.VISIBLE
    }

    override fun totalPrizeCount(uid: Int) {
        binding.prizeText.text = ldb?.totalPrice(uid).toString()
    }

}