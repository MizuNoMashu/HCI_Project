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
import androidx.navigation.Navigation
import com.example.hci.R
import com.example.hci.databinding.FragmentMessagesBinding
import com.example.hci.ldb
import com.example.hci.logged_user


class MessagesFragment : Fragment() {

    private var _binding: FragmentMessagesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var vendor:String ?=null
    val user = logged_user?.name.toString()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMessagesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var listview= binding.listView
        val context: Context? = activity
        //create_contact return an array of vendor name
        val vendorList: ArrayList<String>? = ldb?.create_contact(0,user)!!
        val size = vendorList?.size
        var list = mutableListOf<Model>()
        var count:Int = 0
        while (count < size!! ){   //size not null
            val vendorName = vendorList.get(count)
            list.add(Model(vendorName, "$vendorName description", R.drawable.meinv))
            Log.d("vendor loaded",vendorName)
            count++
            //let the message visible
            listview.adapter = context?.let { MyAdapter(it,R.layout.list_item,list) }
        }



        //to show in app we have to adapt the list to our fragment



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


