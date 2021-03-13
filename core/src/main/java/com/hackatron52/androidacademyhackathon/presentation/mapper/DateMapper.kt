package com.hackatron52.androidacademyhackathon.presentation.mapper

import com.hackatron52.androidacademyhackathon.presentation.model.TimeInterval
import java.text.SimpleDateFormat
import java.util.*

class DateMapper {

    fun mapDate(date: Date?): String =
        date?.let {
            val simpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            simpleDateFormat.format(date)
        } ?: ""

    fun mapTime(date: Date?): String =
        date?.let {
            val simpleDateFormat = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
            simpleDateFormat.format(date)
        } ?: ""

    fun mapTimeInterval(startDate: Date?, endDate: Date?): TimeInterval =
        TimeInterval(mapTime(startDate), mapTime(endDate))

    companion object {
        private const val DATE_FORMAT = "dd MMMM yyyy"
        private const val TIME_FORMAT = "HH:mm"
    }
}