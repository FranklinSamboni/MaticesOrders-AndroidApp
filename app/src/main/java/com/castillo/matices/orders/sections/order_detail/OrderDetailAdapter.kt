package com.castillo.matices.orders.sections.order_detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.castillo.matices.orders.databinding.TemplateProductItemBinding
import com.castillo.matices.orders.models.Product
import com.castillo.matices.orders.viewmodels.ProductViewModel
import kotlinx.android.synthetic.main.template_product_item.view.*

class   OrderDetailAdapter(private val context: Context,
                         var products: List<Product>,
                         private val onProductClickListener: OnProductClickListener,
                         private val onCheckedChangedListener: OnCheckedChangedListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: TemplateProductItemBinding = TemplateProductItemBinding.inflate(layoutInflater, parent, false)
        return ProductViewHolder(binding, onProductClickListener, onCheckedChangedListener)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ProductViewHolder
        val product = products[position]
        viewHolder.bind(product)
    }

    fun updateProducts(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    class ProductViewHolder(val binding: TemplateProductItemBinding,
                            val onProductClickListener: OnProductClickListener,
                            val onCheckedChangedListener: OnCheckedChangedListener) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        init {
            itemView.setOnClickListener(this)
            itemView.stamp_cutted_checkbox.setOnCheckedChangeListener(this)
        }

        override fun onClick(p0: View?) {
            onProductClickListener.onProductClick(adapterPosition)
        }

        override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
            onCheckedChangedListener.onCheckedChanged(adapterPosition, p1)
        }

        fun bind(product: Product) {
            binding.productViewModel = ProductViewModel(product)
            binding.executePendingBindings()
        }

    }

    interface OnProductClickListener {
        fun onProductClick(position: Int)
    }

    interface OnCheckedChangedListener {
        fun onCheckedChanged(position: Int, isChecked: Boolean)
    }

}