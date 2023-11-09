package com.practicum.myhabitreminder.common.diffutil

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.practicum.myhabitreminder.R
import com.practicum.myhabitreminder.domain.models.Habit

class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var mSelectedCategory: String = "animal"

    private val habitTitle = itemView.findViewById<TextView>(R.id.tv_item_title)
    private val habitDescription = itemView.findViewById<TextView>(R.id.tv_item_description)
    private val timeStamp = itemView.findViewById<TextView>(R.id.tv_item_createdTimeStamp)
    private val daysCounter = itemView.findViewById<TextView>(R.id.tv_days_counter)

    fun bind(model: Habit) {
        habitTitle.text = model.title
        habitDescription.text = "${model.description} days"
        timeStamp.text = "Since: ${model.timeStamp}"
        daysCounter.text = "days count: ${model.daysCounter}"
    }
}