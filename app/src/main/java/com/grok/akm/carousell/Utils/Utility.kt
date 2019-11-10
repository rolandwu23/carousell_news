package com.grok.akm.carousell.Utils

import android.content.Context
import android.text.format.DateFormat
import com.grok.akm.carousell.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.roundToInt

object Utility {

    fun getTimeAgo(date: Date?, context: Context): String? {
        if (date == null) {
            return null
        }

        val time = date.time
        if (time <= 0) {
            return null
        }

        val dim = getTimeDistanceInMinutes(time)
        return getReadableStringFromTimeAgo(dim.toLong(), context)
    }

    @Throws(ParseException::class)
    fun parseDateString(date: String, format: String, timeZone: TimeZone): Date {
        val sdf = SimpleDateFormat(format, Locale.ENGLISH)
        sdf.timeZone = timeZone
        return sdf.parse(date)
    }

    fun getDate(time: Long): String {
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = time * 1000
        return DateFormat.format(Constants.DATETIME_FORMAT, cal).toString()

    }

    private fun getReadableStringFromTimeAgo(timeAgoInMinutes: Long, ctx: Context): String {

        val timeAgo: String =  when {
            timeAgoInMinutes < 120 -> "1 " + ctx.resources.getString(R.string.date_util_unit_hour)
            timeAgoInMinutes < 24 * 60 -> (timeAgoInMinutes / 60).toFloat().roundToInt().toString() + " " + ctx.resources.getString(
                R.string.date_util_unit_hours)
            timeAgoInMinutes < 2 * 24 * 60 ->  "1 " + ctx.resources.getString(R.string.date_util_unit_day)
            timeAgoInMinutes < 28 * 24 * 60 -> (timeAgoInMinutes / 1440).toFloat().roundToInt().toString() + " " + ctx.resources.getString(
                R.string.date_util_unit_days)
            timeAgoInMinutes < 2 * 28 * 24 * 60 -> "1 " + ctx.resources.getString(R.string.date_util_unit_month)
            timeAgoInMinutes < 365 * 24 * 60 -> (timeAgoInMinutes / 43200).toFloat().roundToInt().toString() + " " + ctx.resources.getString(
                R.string.date_util_unit_months)
            else -> {
                val year = (timeAgoInMinutes / 525600.0).roundToInt()
                 if (year < 2) {
                    (timeAgoInMinutes / 525600.0).roundToInt().toString() + " " + ctx.resources.getString(
                        R.string.date_util_unit_year
                    )
                } else {
                    (timeAgoInMinutes / 525600.0).roundToInt().toString() + " " + ctx.resources.getString(
                        R.string.date_util_unit_years
                    )
                }
            }
        }

        return timeAgo + " " + ctx.resources.getString(R.string.date_util_suffix)
    }

    private fun getTimeDistanceInMinutes(time: Long): Int {
        val timeDistance = abs(currentDate().time - time)
        return (abs(timeDistance) / 1000 / 60).toFloat().roundToInt()
    }

    private fun currentDate(): Date {
        val calendar = Calendar.getInstance()
        return calendar.time
    }
}
