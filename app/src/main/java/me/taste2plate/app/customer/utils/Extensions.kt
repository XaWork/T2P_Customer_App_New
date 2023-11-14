package me.taste2plate.app.customer.utils

import android.text.Html
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


fun String.toDate(format: String = "dd-MM-yy HH:mm"): String {
    val converter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    converter.timeZone = TimeZone.getTimeZone("+5:30")
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return formatter.format(converter.parse(this)!!)
}

fun String.fromHtml(): String{
    return Html.fromHtml(this, 1).toString()
}