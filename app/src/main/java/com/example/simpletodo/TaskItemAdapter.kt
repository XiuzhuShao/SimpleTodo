package com.example.simpletodo

import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskItemAdapter(val listOfItems: List<String>,
                      val longClickListerner: OnLongClickListener,
                      val shortClickListener: OnShortClickListener)  :
    RecyclerView.Adapter<TaskItemAdapter.ViewHolder>(){

    interface OnLongClickListener {
        fun onItemLongClicked(position: Int)
    }

    interface OnShortClickListener {
        fun onItemShortClicked(position: Int)
    }

    var checkBoxStateArray = SparseBooleanArray()
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        var contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(holder: TaskItemAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val item = listOfItems.get(position)
        holder.textView.text = item
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return listOfItems.size
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        // Stores references to elements in our layout view
        val textView: TextView

        init{
            textView = itemView.findViewById(android.R.id.text1)

            itemView.setOnLongClickListener{
                longClickListerner.onItemLongClicked(adapterPosition)
                true
            }

            itemView.setOnClickListener{
                shortClickListener.onItemShortClicked(adapterPosition)
                true
            }
        }
    }
}