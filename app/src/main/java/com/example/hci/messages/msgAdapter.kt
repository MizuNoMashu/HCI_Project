package com.example.hci.messages

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.hci.R


class msgAdapter (var mCtx: Context, var resources:Int, var items:List<msgModel>) :ArrayAdapter<msgModel>(
    mCtx,resources,items
){
    //function
    override fun getView(position: Int,convertView: View?,parent: ViewGroup): View{

        val layoutInflater:LayoutInflater = LayoutInflater.from(mCtx)
        val view:View=layoutInflater.inflate(resources,null)

        val name: TextView = view.findViewById(R.id.msgName)
        val message:TextView = view.findViewById(R.id.msgMessage)

        var mItem:msgModel = items[position]

        name.text = mItem.name
        message.text = mItem.message

        return view
    }

}