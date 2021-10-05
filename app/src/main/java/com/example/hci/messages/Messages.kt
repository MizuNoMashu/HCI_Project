package com.example.hci.messages


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hci.R
import com.example.hci.databinding.MessagesBinding
import com.example.hci.ldb
import com.example.hci.logged_user


class Messages: Fragment() {

    private var _binding: MessagesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var sendBnt: Button
    private lateinit var clearBtn:Button
    private var listview: ListView? = null
    var arrayList:String? = ""
    var list = mutableListOf<msgModel>()
    var count:Int = 0
    var user:String ?= null
    var vendor:String ?= null

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
        listview= binding.sendView
        sendBnt = binding.sendBtn
        clearBtn = binding.clearBtn

        loadData()

        sendBnt.setOnClickListener{
            var message = binding.inputMessage.text
            list.add(msgModel(logged_user?.name.toString() ,message.toString()))
            listview?.adapter = context?.let { msgAdapter(it,
                com.example.hci.R.layout.message_item,list) }
            binding.inputMessage.setText("")
            saveData(message)
        }


        return root
    }

    private fun saveData(message: Editable?) {
        Log.d("called:","saveData")

        val context: Context? = activity
        val sharedPreferences = context!!.getSharedPreferences("sharedPrefers",
            Context.MODE_PRIVATE)
        vendor = sharedPreferences.getString("VENDOR_KEY",null)

        ldb?.insert_message(count,vendor.toString(), logged_user?.name.toString(),message.toString())

        Log.d("message to save :",message.toString())

        // increment to load all message inserted
        count++

        Log.d("index message :",count.toString())
        Toast.makeText( activity, "saved data", Toast.LENGTH_SHORT).show()

    }


    private fun loadData() {
        Log.d("called: ","load data")
        user = logged_user?.name
        //load vendor
        val context: Context? = activity
        val sharedPreferences = context!!.getSharedPreferences("sharedPrefers",
            Context.MODE_PRIVATE)
        vendor = sharedPreferences.getString("VENDOR_KEY",null)
        Log.d("vendor loaded",vendor.toString())


        val messageList: ArrayList<String>? = ldb?.load_message(vendor.toString(),user.toString())!!
        Log.d("messaggio list",messageList.toString())
        val size = messageList?.size
        Log.d("size of message ",size.toString())



        //load all message of past time
        while (count < size!!){
            val message = messageList.get(count)
            list.add(msgModel(logged_user?.name.toString() ,message.toString()))
            Log.d("message loaded",message.toString())
            count++
            //let the message visible
            listview?.adapter = context?.let { msgAdapter(it,R.layout.message_item,list) }
        }
        Log.d("final count",count.toString())


        //binding.sendView.setText(messagesString)
        Toast.makeText( activity, "loaded", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}