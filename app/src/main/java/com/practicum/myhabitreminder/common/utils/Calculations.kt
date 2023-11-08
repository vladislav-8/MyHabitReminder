package com.practicum.myhabitreminder.common.utils

object Calculations {

    fun cleanDate(_day: Int, _month: Int, _year: Int): String {
        var day = _day.toString()
        var month = _month.toString()

        if (_day < 10) {
            day = "0$_day"
        }

        if (_month < 9) {
            month = "0${_month + 1}"
        } else if (_month in 9..11) {
            month = (_month + 1).toString()
        }

        return "$day/$month/$_year"
    }
}