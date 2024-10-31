package com.ombati.guidecane.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun getCurrentTimeAsString(): String {
    val currentTime = Date()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val currentTimeString = dateFormat.format(currentTime)
    return currentTimeString
}

fun convertDateFormat(dateString: String): String {
    val currentDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    currentDateFormat.timeZone = TimeZone.getTimeZone("UTC")

    val newDateFormat = SimpleDateFormat("EEEE MMMM yyyy, h:mma", Locale.getDefault())

    val date = currentDateFormat.parse(dateString)
    val newDateString = newDateFormat.format(date)
    return newDateString
}
