package com.example.hci.messages



import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
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
    val user = logged_user?.email.toString()
    val bundle_m = Bundle()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMessagesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var listview= binding.listView
        val context: Context? = activity

        val vendorList: ArrayList<String>? = ldb?.create_contact(0,user)!!
        val size = vendorList?.size
        var list = mutableListOf<Model>()
        var count:Int = 0
        while (count < size!! ){   //size not null
            val vendorName = vendorList[count]
            list.add(Model(vendorName, "$vendorName description", R.drawable.meinv))
            count++
            //let the message visible
            listview.adapter = context?.let { MyAdapter(it,R.layout.list_item,list) }
        }


        listview.setOnItemClickListener{
                _: AdapterView<*>?, view:View, position:Int, _:Long ->
            vendor = list[position].title
            bundle_m.putString("vendor", vendor)
            Navigation.findNavController(view).navigate(R.id.messages , bundle_m)

        }

        return root
    }


}


