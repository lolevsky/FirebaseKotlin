package com.example.myapplication

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.view_item.view.*


class ContentAdapter(private val items: List<MyData>, private val listener: OnItemClickListener? = null) : RecyclerView.Adapter<ContentAdapter.ViewHolder>() {

    companion object {
        const val TYPE_DHT = 0
        const val TYPE_SEN = 1
    }

    override fun getItemViewType(position: Int): Int {
        val type = when (items[position].getType()) {
            MyData.TYPE.DHT -> TYPE_DHT
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

        fun bind(content: MyData) = with(itemView) {

            param3.text = "Time = ${content.getDataTime()}"

            when (content.getType()) {
                MyData.TYPE.DHT -> {
                    title.text = "DHT"

                    param1.text = "Temperature = ${content.getParam1()}"
                    param2.text = "Humidity = ${content.getParam2()}"
                }
                else -> {
                    title.text = "SEN"

                    param1.text = "ELD1 = ${content.getParam1()}"
                    param2.text = "ELD2 = ${content.getParam2()}"

                    if (content.getParam1() == "1" || content.getParam2() == "1") {
                        itemView.setBackgroundColor(Color.RED)
                    } else {
                        itemView.setBackgroundColor(Color.WHITE)
                    }
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClickListener(content: MyData)
    }
}
