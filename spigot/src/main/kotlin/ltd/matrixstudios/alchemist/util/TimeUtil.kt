package ltd.matrixstudios.alchemist.util

import org.apache.commons.lang.time.DurationFormatUtils
import java.sql.Timestamp
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


object TimeUtil
{

    private val dateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm")

    fun formatIntoCalendarString(date: Date?): String?
    {
        return dateFormat.format(date)
    }

    fun millisToTimer(millis: Long): String
    {
        val seconds = millis / 1000L
        return if (seconds > 3600L)
        {
            String.format("%02d:%02d:%02d", seconds / 3600L, seconds % 3600L / 60L, seconds % 60L)
        } else String.format("%02d:%02d", seconds / 60L, seconds % 60L)
    }

    fun millisToSeconds(millis: Long): String
    {
        return DecimalFormat("#0.0").format((millis / 1000.0f).toDouble())
    }

    fun dateToString(date: Date?): String
    {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.time.toString()
    }

    fun addDuration(duration: Long): Timestamp
    {
        return truncateTimestamp(Timestamp(System.currentTimeMillis() + duration))
    }

    fun formatDuration(time: Long): String
    {
        if (time == Long.MAX_VALUE)
        {
            return "Forever"
        }

        return DurationFormatUtils.formatDurationWords(time, true, true)
    }

    fun truncateTimestamp(timestamp: Timestamp): Timestamp
    {
        if (timestamp.toLocalDateTime().year > 2037)
        {
            timestamp.year = 2037
        }
        return timestamp
    }

    fun addDuration(timestamp: Timestamp): Timestamp
    {
        return truncateTimestamp(Timestamp(System.currentTimeMillis() + timestamp.time))
    }

    fun fromMillis(millis: Long): Timestamp
    {
        return Timestamp(millis)
    }

    val currentTimestamp: Timestamp
        get() = Timestamp(System.currentTimeMillis())

    fun getTimeZoneShortName(displayName: String): String
    {
        val stz = displayName.split(" ").toTypedArray()
        val sName = StringBuilder()
        for (s in stz)
        {
            sName.append(s[0])
        }
        return sName.toString()
    }

    fun millisToRoundedTime(millis: Long): String
    {
        var millis = millis
        if (millis == Long.MAX_VALUE)
        {
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
        if (years > 0L)
        {
            return years.toString() + " year" + if (years == 1L) "" else "s"
        }
        if (months > 0L)
        {
            return months.toString() + " month" + if (months == 1L) "" else "s"
        }
        if (weeks > 0L)
        {
            return weeks.toString() + " week" + if (weeks == 1L) "" else "s"
        }
        if (days > 0L)
        {
            return days.toString() + " day" + if (days == 1L) "" else "s"
        }
        if (hours > 0L)
        {
            return hours.toString() + " hour" + if (hours == 1L) "" else "s"
        }
        return if (minutes > 0L)
        {
            minutes.toString() + " minute" + if (minutes == 1L) "" else "s"
        } else seconds.toString() + " second" + if (seconds == 1L) "" else "s"
    }

    fun parseToTime(`in`: String): String
    {
        var `in` = `in`
        `in` = `in`.lowercase(Locale.getDefault())
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

    fun parseTime(time: String): Int
    {
        if (time == "0" || time == "")
        {
            return 0
        }

        val lifeMatch = arrayOf("y", "w", "d", "h", "m", "s")
        val lifeInterval = intArrayOf(31_536_000, 604800, 86400, 3600, 60, 1)

        var seconds = -1
        for (i in lifeMatch.indices)
        {
            val matcher = Pattern.compile("([0-9]+)" + lifeMatch[i]).matcher(time)
            while (matcher.find())
            {
                if (seconds == -1)
                {
                    seconds = 0
                }
                seconds += Integer.parseInt(matcher.group(1)) * lifeInterval[i]
            }
        }

        if (time == "perm" || time.equals("Permanent", ignoreCase = true)) return Long.MAX_VALUE.toInt()

        if (seconds == -1)
        {
            throw IllegalArgumentException("Invalid time provided.")
        }

        return seconds
    }
}