package com.example.hci.messages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hci.R

class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    enum class ViewHolderType{
        //type a is user and type b is server messages
        TYPE_A,TYPE_B
    }

    private var list:MutableList<String> = mutableListOf()
    private var timelist:MutableList<String> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ViewHolderType.TYPE_A.ordinal ->{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item,parent,false)
                return UserRecyclerViewHolder(view)
            }
            ViewHolderType.TYPE_B.ordinal ->{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item_server,parent,false)
                return ServerRecyclerViewHolder(view)
            }

            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.message_item, parent, false)
                return UserRecyclerViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is UserRecyclerViewHolder -> {
                holder.contentA = list[position]
                holder.timeA = timelist[position]
                holder.updateView()
            }
            is ServerRecyclerViewHolder -> {
                holder.contentB= list[position]
                holder.timeB = timelist[position]
                holder.updateView()
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
    //load Items
    override fun getItemViewType(position: Int): Int {
        return when(position % 2){
            1 -> ViewHolderType.TYPE_A.ordinal
            0 -> ViewHolderType.TYPE_B.ordinal
            else -> ViewHolderType.TYPE_A.ordinal
        }
    }
    fun reload(list: MutableList<String>,timelist: MutableList<String>){
        this.list.clear()
        this.list.addAll(list)
        this.timelist.clear()
        this.timelist.addAll(timelist)
        notifyDataSetChanged()
    }

    fun loadMore(list: MutableList<String>){
        this.list.addAll(list)
        notifyItemChanged(this.list.size - list.size +1 ,list.size)
    }
}