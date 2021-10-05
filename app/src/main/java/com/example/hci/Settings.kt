package com.example.hci

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.button.MaterialButton

class Settings : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inf = inflater.inflate(R.layout.fragment_settings, container, false)
        //val bundle = this.arguments

        inf.findViewById<MaterialButton>(R.id.button_profile).setOnClickListener{
            Navigation.findNavController(inf).navigate(R.id.action_settings_to_profile2)
        }

        inf.findViewById<MaterialButton>(R.id.button_messages).setOnClickListener {
            Navigation.findNavController(inf).navigate(R.id.action_settings_to_messagesFragment)
        }
        inf.findViewById<MaterialButton>(R.id.payMethod).setOnClickListener {
            Navigation.findNavController(inf).navigate(R.id.action_settings_to_paymentFragment)
        }

        inf.findViewById<MaterialButton>(R.id.logout_button).setOnClickListener {
            logged_user = null
            Navigation.findNavController(inf).navigate(R.id.action_settings_to_loginFragment)
        }


        return inf.rootView
    }
}