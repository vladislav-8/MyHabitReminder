package com.practicum.myhabitreminder.common.diffutil

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.practicum.myhabitreminder.R
import com.practicum.myhabitreminder.domain.models.Habit

class HabitAdapter(
    private val clickListener: HabitClickListener,
    private val longClick: LongHabitClickListener
) :
    RecyclerView.Adapter<HabitViewHolder>() {

    var habits = mutableListOf<Habit>()
        set(newHabits) {
            val diffCallback = DiffUtilCallback(field, newHabits)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newHabits
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.habit_item, parent, false)
        return HabitViewHolder(view)
    }

    override fun getItemCount() = habits.size

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(habits[position])
        holder.itemView.setOnClickListener {
            clickListener.onHabitClick(habits[holder.adapterPosition])
        }
        holder.itemView.setOnLongClickListener {
            longClick.onHabitLongClick(habits[holder.adapterPosition])
            true
        }
    }

    fun interface HabitClickListener {
        fun onHabitClick(habit: Habit)
    }

    fun interface LongHabitClickListener {
        fun onHabitLongClick(habit: Habit)
    }

    fun clearHabits() {
        habits = ArrayList()
    }
}