package com.example.hci.messages


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.hci.R


class MyAdapter(var mCtx: Context, var resources:Int, var items:List<Model>) :ArrayAdapter<Model>(
    mCtx,resources,items
){
    //function
    override fun getView(position: Int,convertView: View?,parent: ViewGroup): View{
        val layoutInflater:LayoutInflater = LayoutInflater.from(mCtx)
        val view:View=layoutInflater.inflate(resources,null)

        val imageView: ImageView=view.findViewById(R.id.profileImage)
        val titleTextView: TextView = view.findViewById(R.id.personName)

        var mItem:Model = items[position]
        imageView.setImageDrawable(mCtx.resources.getDrawable(mItem.img))
        titleTextView.text = mItem.title

        return view
    }

}