package com.example.myapplication

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.view_item.view.*


class ContentAdapter(private val items: List<Update>, private val listener: OnItemClickListener? = null) : RecyclerView.Adapter<ContentAdapter.ViewHolder>() {

    companion object {
        const val TYPE_DHT = 0
        const val TYPE_SEN = 1
    }

    override fun getItemViewType(position: Int): Int {
        val type = when (items[position].updateType) {
            Update.TYPE.DHT -> TYPE_DHT
            else -> TYPE_SEN
        }
        return type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
            = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_item, parent, false))

    override fun getItemCount()
            = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])

        holder.itemView.setOnClickListener { _ ->
            listener?.onItemClickListener(items[position])
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(content: Update) = with(itemView) {

            when (content.updateType) {
                Update.TYPE.DHT -> {
                    title.text = "DHT";
                    param1.text = "Temperature = ${content.logDHT?.temperature}"
                    param2.text = "Humidity = ${content.logDHT?.humidity}"
                    param3.text = "Time = ${content.logDHT?.time}"
                }
                else -> {
                    title.text = "SEN";
                    param1.text = "ELD1 = ${content.logSEN?.eld1}"
                    param2.text = "ELD2 = ${content.logSEN?.eld2}"
                    param3.text = "Time = ${content.logSEN?.time}"

                    if (content.logSEN?.eld1 == 1 || content.logSEN?.eld2 == 1) {
                        itemView.setBackgroundColor(Color.RED)
                    } else {
                        itemView.setBackgroundColor(Color.WHITE)
                    }
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClickListener(content: Update)
    }
}
