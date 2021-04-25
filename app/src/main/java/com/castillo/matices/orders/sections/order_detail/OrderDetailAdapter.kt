package com.castillo.matices.orders.sections.order_detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.castillo.matices.orders.R
import com.castillo.matices.orders.models.Shirt

class OrderDetailAdapter(private val context: Context,
                         private val products: List<Shirt>,
                         private val onProductClickListener: OnProductClickListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shirt_item, parent,false)
        return ProductViewHolder(
            view,
            onProductClickListener
        )
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ProductViewHolder
        val product = products[position]

        /*viewHolder.clientNameTextView.text = product.client?.fullname() ?: ""
        viewHolder.dateCreatedTextView.text = product.dateCreatedFormatted()
        viewHolder.stateTextView.text = product.state?.getDescription(context) ?: ""
        viewHolder.shipperTextView.text = product.shipper*/
    }

    class ProductViewHolder(itemView: View, val onProductClickListener: OnProductClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val clientNameTextView = itemView.findViewById<TextView>(R.id.shirt_stamp_textview)
        val dateCreatedTextView = itemView.findViewById<TextView>(R.id.shirt_size_textview)
        val stateTextView = itemView.findViewById<TextView>(R.id.order_state_textview)
        val shipperTextView = itemView.findViewById<TextView>(R.id.order_shipper_textview)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            onProductClickListener.onProductClick(adapterPosition)
        }

    }

    public interface OnProductClickListener {
        fun onProductClick(position: Int)
    }

}