package com.example.hci.messages

import android.view.View
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.hci.R
import java.lang.ref.WeakReference

class UserRecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    private var view:WeakReference<View> = WeakReference(itemView)
    private var nameView:TextView?=null
    private var textView:TextView?=null
    private var msgView: TextView?=null

    var contentA=""
    var timeA = ""
    init {
        findView()
    }
    private fun findView(){
        textView = view.get()?.findViewById(R.id.msgTime)
        msgView  = view.get()?.findViewById(R.id.msgMessage)
    }

    fun updateView(){
        msgView?.text = contentA
        textView?.text = timeA
    }
}