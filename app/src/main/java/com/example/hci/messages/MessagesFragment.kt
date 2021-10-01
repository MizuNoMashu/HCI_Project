package com.example.hci.messages



import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.hci.R
import com.example.hci.databinding.FragmentMessagesBinding
import com.example.hci.ldb
import com.example.hci.logged_user
import com.example.hci.model.User


class MessagesFragment : Fragment() {

    private var _binding: FragmentMessagesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var vendor:String ?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMessagesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var listview= binding.listView
        val context: Context? = activity

        var list = mutableListOf<Model>()

        list.add(Model("Facebook","facebook description", R.drawable.meinv))
        list.add(Model("Instagram","I description",R.drawable.meinv))
        list.add(Model("Whatapp","W description",R.drawable.meinv))
        list.add(Model("wechat","Wechat description",R.drawable.meinv))
        list.add(Model("booking","B description",R.drawable.meinv))
        list.add(Model("twitter","T description",R.drawable.meinv))

        //to show in app we have to adapt the list to our fragment

        listview.adapter = context?.let { MyAdapter(it,R.layout.list_item,list) }

        //item click listener
        listview.setOnItemClickListener{
                parent: AdapterView<*>?,view:View,position:Int,id:Long ->
            vendor = list[position].title
            Log.d("vendor",vendor.toString())
            saveData()
            Navigation.findNavController(view).navigate(R.id.messages)
            Toast.makeText(context, "click on " + vendor , Toast.LENGTH_SHORT).show()

        }

        return root
    }

    private fun saveData() {
        Log.d("called:","saveData")



        val context: Context? = activity
        val sharedPreferences = context!!.getSharedPreferences("sharedPrefers",
            Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        //save data
        editor.apply{
            putString("VENDOR_KEY",vendor)
            Log.d("saved Data:",vendor.toString())
        }.apply()
        Toast.makeText( activity, "saved data", Toast.LENGTH_SHORT).show()

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


