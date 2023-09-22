package com.practicum.myhabitreminder.common.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.practicum.myhabitreminder.domain.models.Habit

class DiffUtilCallback(
    private val oldList: List<Habit>,
    private val newList: List<Habit>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldHabit = oldList[oldItemPosition]
        val newHabit = newList[newItemPosition]
        return oldHabit.id == newHabit.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldTrack = oldList[oldItemPosition]
        val newTrack = newList[newItemPosition]
        return oldTrack == newTrack
    }
}