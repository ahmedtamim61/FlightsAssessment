package com.example.flightassessment.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

private const val DATE_PATTERN = "yyyy-MM-dd HH:mm"
private const val TIME_PATTERN = "HH:mm"

object DateTimeUtils {

    fun getDateInMillis(value : String) : Long {
        var date : Date? = null
        try {
            date = SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).parse(value)
        } catch (e : ParseException) {
            e.printStackTrace()
        }
        return date?.time ?: 0
    }

    fun convertMillisToHours(millis : Long) : String {
        val hours = TimeUnit.MILLISECONDS.toHours(millis)
        return String.format("%02dh%02dm", hours,
            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(hours))
    }

    fun getTime(value : String) : String {
        var time : String? = null
        try {
            val date = SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).parse(value)
            val timeFormat = SimpleDateFormat(TIME_PATTERN, Locale.getDefault())
            date?.let {
                time = timeFormat.format(date)
            }
        } catch (e : ParseException) {
            e.printStackTrace()
        }
        return time?:""
    }
}