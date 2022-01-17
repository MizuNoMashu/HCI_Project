package com.example.hci.messages


import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private var listview: RecyclerView? = null
    var count:Int = 0
    var user:String ?= null
    val list = mutableListOf<String>()
    private lateinit var  recyclerView:RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: RecyclerViewAdapter

    var vendor: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
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
        if(logged_user == null) NavHostFragment.findNavController(this).navigate(R.id.loginFragment)
        // inizio funzioni
        listview= binding.recyclerView
        sendBnt = binding.sendBtn

        val bundle = this.arguments
        vendor = bundle?.getString("vendor")
        user = logged_user?.email
        recyclerView = binding.recyclerView
        initList()
        reload()


        sendBnt.setOnClickListener{
            var message = binding.inputMessage.text
            list.add(message.toString())
            list.add("Waiting reply by vendor")
            reload()
            binding.inputMessage.setText("")
            saveData(message)

        }


        return root
    }
    private fun initList(){
        layoutManager = LinearLayoutManager(context)
        adapter = RecyclerViewAdapter()

        reload()

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }
    private fun reload(){
        recyclerView.post{
            adapter.reload(loadData())
        }
    }
    private fun loadData():MutableList<String>{

        val messageList: ArrayList<String>? = ldb?.load_message(vendor.toString(),user.toString())!!
        val size = messageList?.size

        while (count<size!!){
            val message = messageList.get(count)
            val odd = count % 2
            when(odd){
                0 ->{
                    list.add(message)
                    count++
                }
                1 ->{
                    list.add(message)
                    count++
                }
                else ->{
                    list.add(message)
                    count++
                }
            }
        }

        return list
    }

    private fun saveData(message: Editable?) {
        Log.d("called:","saveData")
        ldb?.insert_message(count,vendor.toString(), logged_user?.email.toString(),message.toString())
        count++
        ldb?.insert_message(count,vendor.toString(), logged_user?.email.toString(),"Waiting reply by vendor")
        count++
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}