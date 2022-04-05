package ltd.matrixstudios.alchemist.util

import org.apache.commons.lang.time.DurationFormatUtils
import java.sql.Timestamp
import java.text.DecimalFormat
import java.util.*
import java.util.regex.Pattern


object TimeUtil {
    fun millisToTimer(millis: Long): String {
        val seconds = millis / 1000L
        return if (seconds > 3600L) {
            String.format("%02d:%02d:%02d", seconds / 3600L, seconds % 3600L / 60L, seconds % 60L)
        } else String.format("%02d:%02d", seconds / 60L, seconds % 60L)
    }

    fun millisToSeconds(millis: Long): String {
        return DecimalFormat("#0.0").format((millis / 1000.0f).toDouble())
    }

    fun dateToString(date: Date?): String {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.time.toString()
    }

    fun addDuration(duration: Long): Timestamp {
        return truncateTimestamp(Timestamp(System.currentTimeMillis() + duration))
    }

    fun formatDuration(time: Long): String {
        return DurationFormatUtils.formatDurationWords(time, true, true)
    }

    fun truncateTimestamp(timestamp: Timestamp): Timestamp {
        if (timestamp.toLocalDateTime().year > 2037) {
            timestamp.year = 2037
        }
        return timestamp
    }

    fun addDuration(timestamp: Timestamp): Timestamp {
        return truncateTimestamp(Timestamp(System.currentTimeMillis() + timestamp.time))
    }

    fun fromMillis(millis: Long): Timestamp {
        return Timestamp(millis)
    }

    val currentTimestamp: Timestamp
        get() = Timestamp(System.currentTimeMillis())

    fun getTimeZoneShortName(displayName: String): String {
        val stz = displayName.split(" ").toTypedArray()
        val sName = StringBuilder()
        for (s in stz) {
            sName.append(s[0])
        }
        return sName.toString()
    }

    fun millisToRoundedTime(millis: Long): String {
        var millis = millis
        if (millis == Long.MAX_VALUE) {
            return "Permanent"
        }
        ++millis
        val seconds = millis / 1000L
        val minutes = seconds / 60L
        val hours = minutes / 60L
        val days = hours / 24L
        val weeks = days / 7L
        val months = weeks / 4L
        val years = months / 12L
        if (years > 0L) {
            return years.toString() + " year" + if (years == 1L) "" else "s"
        }
        if (months > 0L) {
            return months.toString() + " month" + if (months == 1L) "" else "s"
        }
        if (weeks > 0L) {
            return weeks.toString() + " week" + if (weeks == 1L) "" else "s"
        }
        if (days > 0L) {
            return days.toString() + " day" + if (days == 1L) "" else "s"
        }
        if (hours > 0L) {
            return hours.toString() + " hour" + if (hours == 1L) "" else "s"
        }
        return if (minutes > 0L) {
            minutes.toString() + " minute" + if (minutes == 1L) "" else "s"
        } else seconds.toString() + " second" + if (seconds == 1L) "" else "s"
    }

    fun parseToTime(`in`: String): String {
        var `in` = `in`
        `in` = `in`.toLowerCase()
        `in` = `in`.replace(" ".toRegex(), "")
        `in` = `in`.replace("seconds".toRegex(), "s")
        `in` = `in`.replace("second".toRegex(), "s")
        `in` = `in`.replace("minutes".toRegex(), "m")
        `in` = `in`.replace("minute".toRegex(), "m")
        `in` = `in`.replace("hours".toRegex(), "h")
        `in` = `in`.replace("hour".toRegex(), "h")
        `in` = `in`.replace("days".toRegex(), "d")
        `in` = `in`.replace("day".toRegex(), "d")
        `in` = `in`.replace("weeks".toRegex(), "w")
        `in` = `in`.replace("week".toRegex(), "w")
        `in` = `in`.replace("months".toRegex(), "M")
        `in` = `in`.replace("month".toRegex(), "M")
        `in` = `in`.replace("years".toRegex(), "y")
        `in` = `in`.replace("year".toRegex(), "y")
        return `in`
    }

    fun parseTime(time: String): Long {
        var totalTime = 0L
        var found = false
        val matcher = Pattern.compile("\\d+\\D+").matcher(time)
        while (matcher.find()) {
            val s = matcher.group()
            val value = s.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)").toTypedArray()[0].toLong()
            var s2: String
            s2 = s.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)").toTypedArray()[1]
            val type = s2
            when (s2) {
                "s" -> {
                    totalTime += value
                    found = true
                    continue
                }
                "m" -> {
                    totalTime += value * 60L
                    found = true
                    continue
                }
                "h" -> {
                    totalTime += value * 60L * 60L
                    found = true
                    continue
                }
                "d" -> {
                    totalTime += value * 60L * 60L * 24L
                    found = true
                    continue
                }
                "w" -> {
                    totalTime += value * 60L * 60L * 24L * 7L
                    found = true
                    continue
                }
                "M" -> {
                    totalTime += value * 60L * 60L * 24L * 30L
                    found = true
                    continue
                }
                "y" -> {
                    totalTime += value * 60L * 60L * 24L * 365L
                    found = true
                    continue
                }
            }
        }
        if (time.equals("perm", ignoreCase = true)) return Long.MAX_VALUE
        return if (found) totalTime * 1000L else -1L
    }
}