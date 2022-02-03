package com.example.hci.messages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.hci.R
import com.example.hci.databinding.FragmentMessagebackBinding
import com.example.hci.databinding.MessagesBinding
import java.text.SimpleDateFormat
import java.util.*


class Messageback: Fragment() {
    private var _binding: FragmentMessagebackBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var backBnt: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TAG","messageback: called")

        _binding = FragmentMessagebackBinding.inflate(inflater, container, false)
        val root: View = binding.root
        backBnt=binding.backhome
        backBnt.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.loginFragment)
        }
        return root

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
