package com.example.myapplication.ui.messages


import android.content.Context

import android.os.Bundle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hci.databinding.MessagesBinding


class Messages: Fragment() {

    private var _binding: MessagesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var sendBnt: Button
    private lateinit var textView: TextView
    private lateinit var clearBtn:Button

    var arrayList:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("TAG","onCreate: called")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TAG","onCreateView: called")

        _binding = MessagesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // inizio funzioni
        loadData()

        textView = binding.sendView
        sendBnt = binding.sendBtn
        clearBtn = binding.clearBtn
        clearBtn.setOnClickListener{
            arrayList=""
            binding.sendView.setText("")
        }
        sendBnt.setOnClickListener{
            saveData()
            binding.inputMessage.setText("")
        }


        return root
    }

    private fun saveData() {
        Log.d("called:","saveData")
        val messagestext: String = arrayList + binding.inputMessage.text.toString()+"\n\n"+"Waiting for a answer from admin \n\n "

        arrayList = messagestext
        Log.d("saved Data:",messagestext)

        binding.sendView.setText(messagestext)

        val context: Context? = activity
        val sharedPreferences = context!!.getSharedPreferences("sharedPrefers",
            Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // image encoder

        //save data
        editor.apply{
            putString("MESSAGES_KEY",messagestext)
        }.apply()
        Toast.makeText( activity, "saved data", Toast.LENGTH_SHORT).show()

    }


    private fun loadData() {
        Log.d("called: ","load data")

        val context: Context? = activity
        val sharedPreferences = context!!.getSharedPreferences("sharedPrefers",
            Context.MODE_PRIVATE)

        val messagesString = sharedPreferences.getString("MESSAGES_KEY",null)
        arrayList=messagesString
        binding.sendView.setText(messagesString)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}