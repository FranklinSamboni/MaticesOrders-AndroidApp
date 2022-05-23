package com.castillo.matices.orders.sections.order_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.castillo.matices.orders.databinding.TemplateOrderItemBinding
import com.castillo.matices.orders.models.Order
import com.castillo.matices.orders.viewmodels.OrderViewModel

class OrderAdapter(private val context: Context,
                   var orders: List<Order>,
                   private val onOrderClickListener: OnOrderClickListener
    ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: TemplateOrderItemBinding = TemplateOrderItemBinding.inflate(layoutInflater, parent, false)
        return OrderViewHolder(
            binding,
            onOrderClickListener
        )
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val orderViewHolder = holder as OrderViewHolder
        val order = orders[position]

        orderViewHolder.bind(order)
    }

    fun updateOrders(orders: List<Order>) {
        this.orders = orders
        notifyDataSetChanged()
    }

    class OrderViewHolder(val binding: TemplateOrderItemBinding, val onOrderClickListener: OnOrderClickListener) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            onOrderClickListener.onOrderClick(adapterPosition)
        }

        fun bind(order: Order) {
            binding.orderViewModel = OrderViewModel(order)
            binding.executePendingBindings()
        }
    }

    interface OnOrderClickListener {
        fun onOrderClick(position: Int)
    }

}