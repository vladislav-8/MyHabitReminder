package com.practicum.myhabitreminder.domain.models

data class Habit(
    val id: Int,
    val title: String,
    val description: String,
    val timeStamp: String,
    var daysCounter: Int
)
