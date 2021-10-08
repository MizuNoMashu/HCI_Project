package com.example.hci.payment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.hci.R
import com.example.hci.databinding.FragmentPaymentBinding
import com.example.hci.ldb
import com.example.hci.logged_user


class paymentFragment :Fragment(){

    private var _binding: FragmentPaymentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var vendor:String ?=null
    val user = logged_user?.name.toString()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val context: Context? = activity

        val months = mutableListOf(1)
        for (i in 2..12){
            months.add(i)
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.month_list, months)
        (binding.month.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        val years = mutableListOf(2021)
        for (i in 2022..2065){
            years.add(i)
        }
        val adaptery = ArrayAdapter(requireContext(), R.layout.month_list, years)
        (binding.year.editText as? AutoCompleteTextView)?.setAdapter(adaptery)
        var month_val = "0"
        binding.monthVal.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            month_val  = adapter.getItem(position).toString()
        }
        var year_val = "0"
        binding.yearVal.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            year_val  = adaptery.getItem(position).toString()
        }

        binding.addpay.setOnClickListener() {
            //val ret = binding.cardname.text.toString()+" "+binding.cardnum.text
            logged_user?.email?.let { it1 -> ldb?.insert_payment(it1, binding.cardname.text.toString(), binding.cardnum.text.toString(), month_val+"/"+year_val) }
            NavHostFragment.findNavController(this).navigate(R.id.action_paymentFragment_to_displayPaymentFragment)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}