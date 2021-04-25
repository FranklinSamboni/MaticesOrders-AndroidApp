package com.castillo.matices.orders.sections.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.castillo.matices.orders.R
import com.castillo.matices.orders.models.Order
import com.castillo.matices.orders.models.getDescription

class OrderAdapter(private val context: Context,
                   private val orders: List<Order>,
                   private val onOrderClickListener: OnOrderClickListener
    ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_item, parent,false)
        return OrderViewHolder(view, onOrderClickListener)
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val orderViewHolder = holder as OrderViewHolder
        val order = orders[position]

        orderViewHolder.clientNameTextView.text = order.client?.fullname() ?: ""
        orderViewHolder.dateCreatedTextView.text = order.dateCreatedFormatted()
        orderViewHolder.stateTextView.text = order.state?.getDescription(context) ?: ""
        orderViewHolder.shipperTextView.text = order.shipper
    }

    class OrderViewHolder(itemView: View, val onOrderClickListener: OnOrderClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val clientNameTextView = itemView.findViewById<TextView>(R.id.shirt_stamp_textview)
        val dateCreatedTextView = itemView.findViewById<TextView>(R.id.shirt_size_textview)
        val stateTextView = itemView.findViewById<TextView>(R.id.order_state_textview)
        val shipperTextView = itemView.findViewById<TextView>(R.id.order_shipper_textview)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            onOrderClickListener.onOrderClick(adapterPosition)
        }

    }

    public interface OnOrderClickListener {
        fun onOrderClick(position: Int)
    }

}