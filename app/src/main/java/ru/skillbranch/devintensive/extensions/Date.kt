package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECONDS = 1000L
const val MINUTE = 60* SECONDS
const val HOUR = 60* MINUTE
const val DAY = 24* HOUR

fun Date.format(pattern: String="HH.mm.ss.yy"): String{
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

    fun Date.add (value:Int, TimeUnits:String):Date {
        var time = this.time

        when (TimeUnits) {
            "second" , "seconds" -> value * SECONDS
            "minute" , "minutes" -> value * MINUTE
            "hour" , "hours" -> value * HOUR
            "day" , "days" -> value * DAY
            else -> throw IllegalStateException("invalid unit")
        }
this.time=time
        return this
    }
