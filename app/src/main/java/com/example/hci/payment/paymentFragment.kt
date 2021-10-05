package com.example.hci.payment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hci.databinding.FragmentPaymentBinding
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


            return root
        }


        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
}