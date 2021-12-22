package com.example.hci

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.hci.adapter.ItemAdapter
import com.example.hci.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            if(logged_user == null) NavHostFragment.findNavController(this).navigate(R.id.action_scroll_products_to_loginFragment)
            _binding = FragmentSearchBinding.inflate(inflater,container,false)
            val bundle = this.arguments
            val bundleUpdate = Bundle()
            binding.filterVal.setAdapter(ArrayAdapter(requireContext() ,android.R.layout.simple_spinner_item, arrayOf("Rising Price","Decreasing Price","Value for Money","User Rating")))
            binding.filterVal.setText(binding.filterVal.adapter.getItem(bundle!!.getInt("filter")!!).toString(),false)
            binding.filterVal.setOnItemClickListener{_,_,position,_ ->
                when(position){
                    0->{
                        bundleUpdate.putString("query" , bundle!!.getString("query"))
                        bundleUpdate.putInt("filter", 0)
                        NavHostFragment.findNavController(this).navigate(R.id.action_searchFragment_self,bundleUpdate)
                    }
                    1->{
                        bundleUpdate.putString("query" , bundle!!.getString("query"))
                        bundleUpdate.putInt("filter", 1)
                        NavHostFragment.findNavController(this).navigate(R.id.action_searchFragment_self,bundleUpdate)
                    }
                    2->{
                        bundleUpdate.putString("query" , bundle!!.getString("query"))
                        bundleUpdate.putInt("filter", 2)
                        NavHostFragment.findNavController(this).navigate(R.id.action_searchFragment_self,bundleUpdate)
                    }
                    3->{
                        bundleUpdate.putString("query" , bundle!!.getString("query"))
                        bundleUpdate.putInt("filter", 3)
                        NavHostFragment.findNavController(this).navigate(R.id.action_searchFragment_self,bundleUpdate)
                    }
                }
            }
            binding.scrollSearch.adapter = ItemAdapter( ldb?.select_product_search(bundle!!.getString("query")!!,bundle!!.getInt("filter")!!)!!)
            binding.scrollSearch.setHasFixedSize(true)
            return binding.root
        }



}