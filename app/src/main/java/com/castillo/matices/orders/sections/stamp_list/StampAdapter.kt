package com.castillo.matices.orders.sections.stamp_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.castillo.matices.orders.databinding.TemplateStampItemBinding
import com.castillo.matices.orders.models.Stamp

class StampAdapter(private val context: Context,
                   var stamps: List<Stamp>,
                   private val onStampClickListener: OnStampClickListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: TemplateStampItemBinding = TemplateStampItemBinding.inflate(layoutInflater, parent, false)
        return StampViewHolder(
            binding,
            onStampClickListener
        )
    }

    override fun getItemCount(): Int {
        return stamps.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as StampViewHolder
        val stamp = stamps[position]

        viewHolder.bind(stamp)
    }

    fun updateStamps(stamps: List<Stamp>) {
        this.stamps = stamps
        notifyDataSetChanged()
    }

    class StampViewHolder(val binding: TemplateStampItemBinding, val onStampClickListener: OnStampClickListener) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            onStampClickListener.onStampClick(adapterPosition)
        }

        fun bind(stamp: Stamp) {
            binding.stamp = stamp
            binding.executePendingBindings()
        }
    }

    interface OnStampClickListener {
        fun onStampClick(position: Int)
    }

}