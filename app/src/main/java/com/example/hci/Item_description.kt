package com.example.hci


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Item_description : Fragment() {
    //private var _binding: Fragment? = null
    //private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inf = inflater.inflate(R.layout.fragment_item_description, container, false)
        val bundle = this.arguments
        //_binding = Item_description.inflate(inflater,container,false)
        if (bundle != null) {
            //inf.
            inf.findViewById<TextView>(R.id.item_name).text = bundle.getString("title")
            inf.findViewById<TextView>(R.id.count_rating).text = bundle.getString("reviews")
            inf.findViewById<TextView>(R.id.price).text = bundle.getString("price")
            inf.findViewById<ImageView>(R.id.item_image).setImageResource(bundle.getInt("image"))
            inf.findViewById<RatingBar>(R.id.stars).rating = bundle.getFloat("stars")
        }

        val quantityButton = inf.findViewById<MaterialButton>(R.id.item_quantity)

        quantityButton.setOnClickListener {
            val x = android.app.Dialog(requireContext())
            x.setContentView(R.layout.quantity_dialog)
            x.show()
            x.findViewById<MaterialButton>(R.id.close_quantity).setOnClickListener{
                x.dismiss()
            }

            //x.window?.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        }

        return inf.rootView
    }

}