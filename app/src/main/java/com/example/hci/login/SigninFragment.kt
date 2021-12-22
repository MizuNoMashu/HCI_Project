package com.example.hci.login

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.hci.R
import com.example.hci.databinding.FragmentSigninBinding


import com.example.hci.ldb
import com.example.hci.logged_user

class SigninFragment : Fragment() {

    private var _binding: FragmentSigninBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSigninBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (logged_user != null) NavHostFragment.findNavController(this)
            .navigate(R.id.action_loginFragment_to_scroll_products)

        val emailEditText = binding.emails
        val nameEditText = binding.names
        val surnameEditText = binding.surnames
        val passwordEditText = binding.passwords
        val password2EditText = binding.password2
        val addressEditText = binding.address
        val phoneEditText = binding.phone

        val signinButton = binding.signins
        val loginButton = binding.logins

        binding.errorTexts.visibility = View.GONE

        signinButton.setOnClickListener {

            val emailText = emailEditText.text.toString()
            val nameText = nameEditText.text.toString()
            val surnameText = surnameEditText.text.toString()
            val passwordText = passwordEditText.text.toString()
            val password2Text = password2EditText.text.toString()
            val addressText = addressEditText.text.toString()
            val phoneText = phoneEditText.text.toString()

            if(!emailText.contains("@") || emailText == ""){
               // showInvalidEmail()
            } else if(nameText == ""){
                showInvalidName()
            } else if(passwordText.length < 5 || password2Text.length < 5){
                showInvalidPassword()
            } else if (passwordText != password2Text){
                showPasswordMismatch()
            } else {
                val ret = ldb?.insert_user(emailText, nameText, surnameText, passwordText, addressText, phoneText)
                if(ret == 0){
                    logged_user = ldb?.select_user(emailText)
                    NavHostFragment.findNavController(this).navigate(R.id.action_signinFragment_to_scroll_products)
                } else{
                    showSigninFailed()
                }
            }
        }
        loginButton.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_signinFragment_to_loginFragment)
        }
    }

    private fun showSigninFailed() {
        displayMsg("Error sign in")
    }

    private fun showInvalidEmail(){
        displayMsg("Invalid email")
    }

    private fun showInvalidName(){
        displayMsg("Name can't be empty")
    }

    private fun showInvalidPassword(){
        displayMsg("Password must have min 5 char")
    }

    private fun showPasswordMismatch(){
        displayMsg("Passwords don't coincide")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun displayMsg(str: String){
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, str, Toast.LENGTH_LONG).show()
        val errtxt = binding.errorTexts
        errtxt.text = str
        errtxt.visibility = View.VISIBLE
        Handler().postDelayed(Runnable { // hide your button here
            errtxt.visibility = View.GONE
        }, 2000)

    }
}