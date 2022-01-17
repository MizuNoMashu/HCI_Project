package com.example.hci.payment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
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
        if(logged_user == null) NavHostFragment.findNavController(this).navigate(R.id.loginFragment)

        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val context: Context? = activity
        binding.errorTextc.visibility = View.GONE

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
            if(month_val == "0"){
                showMsg("Month")
            } else if (year_val == "0"){
                showMsg("Year")
            } else if (binding.cardname.text.toString() == ""){
                showMsg("Card name")
            }else if (binding.cardnum.text.toString() == ""){
                showMsg("Card number")
            } else{
                logged_user?.email?.let { it1 -> ldb?.insert_payment(it1, binding.cardnum.text.toString(), binding.cardname.text.toString(), month_val+"/"+year_val) }
                NavHostFragment.findNavController(this).navigate(R.id.action_paymentFragment_to_displayPaymentFragment)
            }
        }

        return root
    }

    private fun displayMsg(str: String){
        val errtxt = binding.errorTextc
        errtxt.text = str
        errtxt.visibility = View.VISIBLE
        Handler().postDelayed(Runnable { // hide your button here
            errtxt.visibility = View.GONE
        }, 2000)
    }

    private fun showMsg(msg: String){
        displayMsg(msg + " can't be empty")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}