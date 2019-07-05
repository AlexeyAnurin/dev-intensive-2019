package ru.skillbranch.devintensive.extensions

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs


//Каждая enum-константа является объектом( экземплярами enum-класса)
//могут быть инициализированы
enum class TimeUnits(val durationInMs: Long, val one: String, val few: String, val many: String) {
    SECOND(1000L, "секунду", "секунды", "секунд"),
    MINUTE(60000L, "минуту", "минуты", "минут"),
    HOUR(3600000L, "час", "часа", "часов"),
    DAY(86400000L, "день", "дня", "дней")
}

fun TimeUnits.plural(value: Int): String {
    val tens = value % 100 / 10
    val ones = value % 10
    return when {
        tens == 1 || ones == 0 || ones >= 5 -> "$value $many"
        ones == 1 -> "$value $one"
        else -> "$value $few"
    }
}

fun String.toDate(pattern: String = "HH:mm:ss dd.MM.yy"): Date? = try {
    SimpleDateFormat(pattern, Locale("ru", "RU")).parse(this)
} catch (e: ParseException) {
    Log.e("DEV", "${javaClass.simpleName} toDate: $e")
    null
}

// from JSLO
fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

/*fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String = SimpleDateFormat(pattern, Locale("ru")).format(this)*/

fun Date.add(value: Int, timeUnit: TimeUnits = TimeUnits.SECOND): Date = this.apply {
    time += when (timeUnit) {
        TimeUnits.SECOND -> value * TimeUnits.SECOND.durationInMs
        TimeUnits.MINUTE -> value * TimeUnits.MINUTE.durationInMs
        TimeUnits.HOUR -> value * TimeUnits.HOUR.durationInMs
        TimeUnits.DAY -> value * TimeUnits.DAY.durationInMs
    }
}

fun Date.humanizeDiff(otherDate: Date = Date()): String {
    val isPast = time < otherDate.time
    val diff = abs(time - otherDate.time)
    val diffDays = (diff / TimeUnits.DAY.durationInMs).toInt()
    val diffHours = (diff / TimeUnits.HOUR.durationInMs).toInt()
    val diffMinutes = (diff / TimeUnits.MINUTE.durationInMs).toInt()
    val diffSeconds = (diff / TimeUnits.SECOND.durationInMs).toInt()
    return if (isPast) when {
        diffDays > 360 -> "более года назад"
        diffHours > 26 -> "${TimeUnits.DAY.plural(diffDays)} назад"
        diffHours in 23..26 -> "день назад"
        diffHours <= 22 && diffMinutes > 75 -> "${TimeUnits.HOUR.plural(diffHours)} назад"
        diffMinutes in 46..75 -> "час назад"
        diffMinutes <= 45 && diffSeconds > 75 -> "${TimeUnits.MINUTE.plural(diffMinutes)} назад"
        diffSeconds in 46..75 -> "минуту назад"
        diffSeconds in 2..45 -> "несколько секунд назад"
        else -> "только что"
    } else when {
        diffDays > 360 -> "более чем через год"
        diffHours > 26 -> "через ${TimeUnits.DAY.plural(diffDays)}"
        diffHours in 23..26 -> "через день"
        diffHours <= 22 && diffMinutes > 75 -> "через ${TimeUnits.HOUR.plural(diffHours)}"
        diffMinutes in 46..75 -> "через час"
        diffMinutes <= 45 && diffSeconds > 75 -> "через ${TimeUnits.MINUTE.plural(diffMinutes)}"
        diffSeconds in 46..75 -> "через минуту"
        diffSeconds in 2..45 -> "через несколько секунд"
        else -> "только что"
    }
}
