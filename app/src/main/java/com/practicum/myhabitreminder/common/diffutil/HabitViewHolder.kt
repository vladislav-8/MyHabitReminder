package com.practicum.myhabitreminder.common.diffutil

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.practicum.myhabitreminder.R
import com.practicum.myhabitreminder.domain.models.Habit

class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val habitTitle = itemView.findViewById<TextView>(R.id.tv_item_title)
    private val habitDescription = itemView.findViewById<TextView>(R.id.tv_item_description)

    fun bind(model: Habit) {
        habitTitle.text = model.title
        habitDescription.text = model.description
    }
}