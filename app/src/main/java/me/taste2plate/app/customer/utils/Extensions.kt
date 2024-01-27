package me.taste2plate.app.customer.utils

import android.icu.text.DecimalFormat
import android.text.Html
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


fun String.toDate(format: String = "dd-MM-yy HH:mm"): String {
    val converter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    converter.timeZone = TimeZone.getTimeZone("+5:30")
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return formatter.format(converter.parse(this)!!)
}

fun String.fromHtml(): String {
    return Html.fromHtml(this, 1).toString()
}


fun String.toDateObject(format: String = "dd-MM-yy HH:mm"): Date {
    val converter = SimpleDateFormat(format, Locale.getDefault())
    return converter.parse(this)!!
}

fun String.toDecimal(): Double {
    return String.format("%.2f", if(this.isEmpty()) 0.0 else this.toDouble()).toDouble()
    //val decimalFormat = DecimalFormat("#.##")
    //return decimalFormat.format(this.toDouble()).toDouble()
}