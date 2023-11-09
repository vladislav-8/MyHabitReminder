package com.practicum.myhabitreminder.common.db.converters

import com.practicum.myhabitreminder.common.db.entity.HabitEntity
import com.practicum.myhabitreminder.domain.models.Habit

class ConverterDb {

    fun mapFromHabitEntityToHabit(from: HabitEntity): Habit {
        return Habit(
            id = from.id,
            title = from.title!!,
            description = from.description!!,
            timeStamp = from.timeStamp!!,
            daysCounter = from.daysCounter!!
        )
    }

    fun mapFromHabitToHabitEntity(from: Habit): HabitEntity {
        return HabitEntity(
            id = from.id,
            title = from.title,
            description = from.description,
            timeStamp = from.timeStamp,
            daysCounter = from.daysCounter
            )
    }
}