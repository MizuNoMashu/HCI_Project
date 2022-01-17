package com.example.hci.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.hci.R
import com.example.hci.ldb
import com.example.hci.logged_user
import com.example.hci.model.Payment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PaymentAdapter(private val dataset: MutableList<Payment>
): RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder>() {

    class PaymentViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        val pnum = view.findViewById<TextView>(R.id.card_num)
        val pname = view.findViewById<TextView>(R.id.card_name)
        val pexpire = view.findViewById<TextView>(R.id.card_exp)
        val removepayment = view.findViewById<ImageView>(R.id.rmvpayment)

        fun bindPayment(pay: Payment){
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
        holder.removepayment.setOnClickListener{
            MaterialAlertDialogBuilder(holder.removepayment.context)
                .setTitle("Remove Payment method")
                .setMessage("Are you sure that you wnant to remove this payment method?")
                .setNeutralButton("Cancel"){ dialog, which ->

                }
                .setPositiveButton("Yes"){dialog, which ->
                    //remove from db
                    logged_user?.email?.let { it1 -> ldb?.remove_payment(it1,
                        holder.pnum.text as String
                    ) }
                    dataset.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, itemCount)
                    //NavHostFragment.findNavController(this).navigate(R.id.loginFragment)
                }
                .show()
        }

    }

    override fun getItemCount() = dataset.size
}