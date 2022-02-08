package com.example.hci.login

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.hci.R
import com.example.hci.databinding.FragmentLoginBinding
import com.example.hci.ldb
import com.example.hci.logged_user
import com.example.hci.model.User
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)?.visibility = View.GONE
        activity?.findViewById<MaterialToolbar>(R.id.topAppBar)?.menu?.findItem(R.id.search)?.isVisible = false
        activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)?.menu?.findItem(R.id.home)?.isChecked = true
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(logged_user != null) NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_scroll_products)

        val usernameEditText = binding.username
        val passwordEditText = binding.password

        val loginButton = binding.login
        val signinButton = binding.signin
        val googleButton = binding.glogin

        val eyeButton = binding.eye

        binding.errorText.visibility = View.GONE
        binding.frgpw.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG)

        loginButton.setOnClickListener {
            val ret = ldb?.select_user(usernameEditText.text.toString())
            if(ret == null){
                showLoginFailed()
            } else{
                if(passwordEditText.text.toString() == ret.password){
                    updateUiWithUser(ret)
                }
                else showLoginFailed()
            }
        }
        signinButton.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_signinFragment)
        }
        googleButton.setOnClickListener {
            val ret = ldb?.select_user("ciao@ok.oi")
            if(ret == null){
                showLoginFailed()
            } else{
                if("prova" == ret.password){
                    updateUiWithUser(ret)
                }
                else showLoginFailed()
            }
        }
        //val appContext = context?.applicationContext ?: return
        binding.frgpw.setOnClickListener {
            showDefaultDialog()
            //NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_scroll_products)
        }

        var eyeval = 0
        eyeButton.setOnClickListener {
            if(eyeval == 0){
                passwordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                eyeval = 1
                context?.let { it1 -> ContextCompat.getColor(it1, R.color.primary) }
                    ?.let { it2 -> eyeButton.setColorFilter(it2, android.graphics.PorterDuff.Mode.SRC_IN) };
            } else{
                passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
                eyeval = 0
                context?.let { it1 -> ContextCompat.getColor(it1, R.color.black) }
                    ?.let { it2 -> eyeButton.setColorFilter(it2, android.graphics.PorterDuff.Mode.SRC_IN) };
            }
        }
    }

    private fun showDefaultDialog() {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.apply {
            setTitle("Recover password")
            setMessage("An email has been sent to the specified address")
            setPositiveButton("Ok", null)
        }.create().show()
    }

    private fun updateUiWithUser(usr: User) {
        //val welcome = getString(R.string.welcome) + usr.name
        logged_user = usr
        val appContext = context?.applicationContext ?: return
        //val toast0 = Toast.makeText(appContext, welcome, Toast.LENGTH_LONG)
        //toast0.show()
        //activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)?.visibility = View.VISIBLE
        NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_scroll_products)
    }

    private fun showLoginFailed() {
        //val appContext = context?.applicationContext ?: return
        //val toast0 = Toast.makeText(appContext, "Error login", Toast.LENGTH_LONG)
        //toast0.show()
        val errtxt = binding.errorText
        errtxt.text = "Error login"
        errtxt.visibility = View.VISIBLE
        Handler().postDelayed(Runnable {
            errtxt.visibility = View.GONE
        }, 2000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}