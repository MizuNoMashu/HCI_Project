package com.example.hci

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.affirmations.adapter.ItemAdapter
import com.example.hci.databinding.FragmentVendorBinding

class VendorFragment : Fragment() {
    private var _binding: FragmentVendorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if(logged_user == null) NavHostFragment.findNavController(this).navigate(R.id.loginFragment)
        //val vview = inflater.inflate(R.layout.fragment_vendor,container,false)
        _binding = FragmentVendorBinding.inflate(inflater, container, false)
        val bundle = this.arguments
        var vendor: String? = null
        if (bundle != null) {
            vendor = ldb?.select_vendor(bundle.getInt("id_vendor"))
        }

        binding.venName.text = vendor
        //binding.venEval.text = ""
        //binding.venDescr.text = ""

        val recyclerView = _binding!!.recyclerViewVen
        if (bundle != null) {
            recyclerView.adapter = ItemAdapter( bundle.getInt("id_vendor").let { ldb?.select_product_by_vendor(it) }!!)
        }
        recyclerView.setHasFixedSize(true)

        binding.chatbtn.setOnClickListener {
            val bundle_m = Bundle()
            bundle_m.putString("vendor", vendor)
            // if vendor exits into database t = 1 else t = 0
            var t = ldb?.verify_contact(vendor.toString())

            if (t.toString() == "1"){
                NavHostFragment.findNavController(this).navigate(R.id.messages, bundle_m)
            } else{
                ldb?.insert_message(0,vendor.toString(), logged_user?.email.toString(),"Now we are friends, you can chat at all")
                NavHostFragment.findNavController(this).navigate(R.id.messages,bundle_m)
            }
        }

        return _binding!!.root
    }

}