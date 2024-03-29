package com.example.hci

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.contracts.contract

class Settings : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inf = inflater.inflate(R.layout.fragment_settings, container, false)
        //val bundle = this.arguments
        if(logged_user == null) NavHostFragment.findNavController(this).navigate(R.id.loginFragment)
        val usr = logged_user?.email?.let { ldb?.select_user(it) }

        inf.findViewById<TextView>(R.id.nameUsr).text = usr?.name
        inf.findViewById<TextView>(R.id.emailusr).text = usr?.email
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
            var verifymessage = ldb?.verify_message()

            if (verifymessage.toString() !="1") {
                Log.d("TAG","verifymessage:called ")
                Navigation.findNavController(inf).navigate(R.id.action_settings_to_messageback)
            }else{
                Navigation.findNavController(inf).navigate(R.id.action_settings_to_messagesFragment)}        }

        inf.findViewById<MaterialButton>(R.id.logout_button).setOnClickListener {
            val context: Context? = activity

            MaterialAlertDialogBuilder(context!!)
                .setTitle("Log out")
                .setMessage("Are you sure to log out?")
                .setNeutralButton(R.string.cancel) { dialog, which ->
                    // Respond to neutral button press
                }
                .setPositiveButton("YES") { dialog, which ->
                    logged_user = null
                    Navigation.findNavController(inf).navigate(R.id.action_settings_to_loginFragment)
                }
                .show()
        }

        inf.findViewById<MaterialButton>(R.id.payMethod).setOnClickListener {
            Navigation.findNavController(inf).navigate(R.id.action_settings_to_displayPaymentFragment)
        }

        inf.findViewById<MaterialButton>(R.id.order).setOnClickListener {
            Navigation.findNavController(inf).navigate(R.id.action_settings_to_orderFragment)
        }

        return inf.rootView
    }
}