package com.example.hci


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        }

        val quantityButton = binding.itemQuantity

        quantityButton.setOnClickListener {
            val x = android.app.Dialog(requireContext())
            x.setContentView(R.layout.quantity_dialog)
            x.show()
            x.findViewById<MaterialButton>(R.id.close_quantity).setOnClickListener{
                x.dismiss()
            }


        }

        return binding.root
    }

}