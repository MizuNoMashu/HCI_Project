package com.example.hci


import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.hci.databinding.FragmentItemDescriptionBinding
import com.google.android.material.button.MaterialButton


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Item_description : Fragment() {
    private var _binding: FragmentItemDescriptionBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val bundle = this.arguments
        _binding = FragmentItemDescriptionBinding.inflate(inflater,container,false)
        if (bundle != null) {
            binding.itemName.text = bundle.getString("title")
            binding.countRating.text = bundle.getString("reviews")
            binding.price.text = bundle.getString("price")
            binding.itemImage.setImageResource(bundle.getInt("image"))
            binding.stars.rating = bundle.getFloat("stars")
            binding.vendorName.text = ldb?.select_vendor(bundle.getInt("id_vendor"))?.name
            binding.vendorName.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG)
        }


        val quantityButton = binding.itemQuantity
        var quantity = 1
        quantityButton.setOnClickListener {
            val x = android.app.Dialog(requireContext())
            x.setContentView(R.layout.quantity_dialog)
            val width_text_quantity = x.findViewById<TextView>(R.id.text_quantity).width


            val scrollQuantity =  x.findViewById<ListView>(R.id.scroll_quantity)
            scrollQuantity.layoutParams.width = width_text_quantity
            val adapter = ArrayAdapter(requireContext() , R.layout.quantity_dialog_item , (1..31).toList())
            scrollQuantity.adapter = adapter
            x.show()
            x.findViewById<MaterialButton>(R.id.close_quantity).setOnClickListener{
                x.dismiss()
            }
            scrollQuantity.setOnItemClickListener{ _, _, position, _ ->
                quantity = adapter.getItem(position)!!
                val temp = "Qty: " + quantity.toString()
                binding.itemQuantity.text =  temp
                x.dismiss()
                Toast.makeText( activity, "Item added", Toast.LENGTH_SHORT).show()
            }
        }
        val bundle_v = Bundle()
        if (bundle != null) {
            bundle_v.putInt("id_vendor", bundle.getInt("id_vendor"))
        }


        binding.addToCart.setOnClickListener{
            if (bundle != null) {
                ldb?.add_to_cart(logged_user!!.id , bundle.getInt("id_vendor") ,
                    bundle.getString("title")!! , bundle.getString("price")!!.toFloat(),bundle.getInt("image"), quantity )
                Toast.makeText( activity, "Item added", Toast.LENGTH_SHORT).show()
            }
        }

        binding.vendorName.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_item_description_to_vendorFragment, bundle_v)
        }

        return binding.root
    }

}