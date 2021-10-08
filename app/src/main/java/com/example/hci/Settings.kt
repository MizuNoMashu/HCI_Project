package com.example.hci

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
        inf.findViewById<TextView>(R.id.nameUsr).text = logged_user?.name
        inf.findViewById<TextView>(R.id.emailusr).text = logged_user?.email
        val img = logged_user?.email?.let { ldb?.getImage(it) }
        if (img != null) {
            val imageBytes = Base64.decode(img, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            inf.findViewById<ImageView>(R.id.imgUsr).setImageBitmap(decodedImage)
        }

        inf.findViewById<MaterialButton>(R.id.button_profile).setOnClickListener{
            Navigation.findNavController(inf).navigate(R.id.action_settings_to_profile2)
        }

        inf.findViewById<MaterialButton>(R.id.button_messages).setOnClickListener {
            Navigation.findNavController(inf).navigate(R.id.action_settings_to_messagesFragment)
        }

        inf.findViewById<MaterialButton>(R.id.logout_button).setOnClickListener {
            logged_user = null
            Navigation.findNavController(inf).navigate(R.id.action_settings_to_loginFragment)
        }

        inf.findViewById<MaterialButton>(R.id.payMethod).setOnClickListener {
            Navigation.findNavController(inf).navigate(R.id.action_settings_to_paymentFragment)
        }

        inf.findViewById<MaterialButton>(R.id.order).setOnClickListener {
            Navigation.findNavController(inf).navigate(R.id.action_settings_to_orderFragment)
        }

        return inf.rootView
    }
}