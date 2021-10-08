package com.example.hci.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hci.R
import com.example.hci.model.Payment

class PaymentAdapter(private val dataset: MutableList<Payment>
): RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder>() {

    class PaymentViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        fun bindPayment(pay: Payment){
            val pnum = view.findViewById<TextView>(R.id.card_num)
            val pname = view.findViewById<TextView>(R.id.card_name)
            val pexpire = view.findViewById<TextView>(R.id.card_exp)

            pnum.text = pay.number
            pname.text = pay.name
            pexpire.text = pay.expire
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.payment_item, parent, false)

        return PaymentViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        holder.bindPayment(dataset[position])
    }

    override fun getItemCount() = dataset.size
}