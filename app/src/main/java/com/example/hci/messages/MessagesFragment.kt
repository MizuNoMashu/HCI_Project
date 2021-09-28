package com.example.hci.messages



import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.hci.R
import com.example.hci.databinding.FragmentMessagesBinding


class MessagesFragment : Fragment() {


    private var _binding: FragmentMessagesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
        list.add(Model("Instagram","facebook description",R.drawable.meinv))
        list.add(Model("Facebook","facebook description",R.drawable.meinv))
        list.add(Model("Facebook","facebook description",R.drawable.meinv))
        list.add(Model("Facebook","facebook description",R.drawable.meinv))
        list.add(Model("Facebook","facebook description",R.drawable.meinv))

        //to show in app we have to adapt the list to our fragment

        listview.adapter = context?.let { MyAdapter(it,R.layout.list_item,list) }

        //item click listener
        listview.setOnItemClickListener{
            parent: AdapterView<*>?,view:View,position:Int,id:Long ->
            if (position==0){
                Navigation.findNavController(view).navigate(R.id.messages)
                Toast.makeText(context, "click on 0", Toast.LENGTH_SHORT).show()
            }
            if (position==1){
                Navigation.findNavController(view).navigate(R.id.messages)
                Toast.makeText(context, "click on 1", Toast.LENGTH_SHORT).show()
            }
            if (position==2){
                Toast.makeText(context, "click on 2", Toast.LENGTH_SHORT).show()
            }
            if (position==3){
                Toast.makeText(context, "click on 3", Toast.LENGTH_SHORT).show()
            }
            if (position==4){
                Toast.makeText(context, "click on 4", Toast.LENGTH_SHORT).show()
            }
            if (position==5){
                Toast.makeText(context, "click on 5", Toast.LENGTH_SHORT).show()
            }
            if (position==6){
                Toast.makeText(context, "click on 6", Toast.LENGTH_SHORT).show()
            }
            if (position==7){
                Toast.makeText(context, "click on 7", Toast.LENGTH_SHORT).show()
            }


        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}