package com.example.hci.messages

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hci.R
import java.lang.ref.WeakReference


class ServerRecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    private var view: WeakReference<View> = WeakReference(itemView)
    private var nameView: TextView?=null
    private var msgView: TextView?=null

    var contentB=""
    init {
        findView()
    }
    private fun findView(){
        nameView = view.get()?.findViewById(R.id.msgName)
        msgView  = view.get()?.findViewById(R.id.msgMessage)
    }

    fun updateView(){
        msgView?.text = contentB
    }
}