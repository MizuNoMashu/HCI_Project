package com.example.hci.payment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.hci.R
import com.example.hci.adapter.PaymentAdapter
import com.example.hci.databinding.FragmentDisplPayBinding
import com.example.hci.ldb
import com.example.hci.logged_user

class DisplayPaymentFragment : Fragment(){
    private var _binding: FragmentDisplPayBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if(logged_user == null) NavHostFragment.findNavController(this).navigate(R.id.loginFragment)
        _binding = FragmentDisplPayBinding.inflate(inflater, container, false)

        val recyclerView = _binding!!.recyclerpay
        val ret = ldb?.getPayments()
        if (ret != null) {
            Log.d("db", ret.size.toString())
            recyclerView.adapter = PaymentAdapter(ret)
        }

        recyclerView.setHasFixedSize(true)

        binding.addnewpay.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_displayPaymentFragment_to_paymentFragment)
        }

        return _binding!!.root
    }
}