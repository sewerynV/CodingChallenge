package com.seweryn.codechallenge.utils

import com.seweryn.codechallenge.R
import com.seweryn.codechallenge.utils.extensions.dayDifferenceFromToday
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class DateFormatterImpl(private val stringProvider: StringProvider) : DateFormatter {
    override fun formatDateForDisplay(date: LocalDateTime): String {
        val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyy")
        val timeFormatter = DateTimeFormatter.ofPattern("hh:mm")
        return when(date.toLocalDate().dayDifferenceFromToday()) {
            -1L -> String.format(stringProvider.getString(R.string.date_format_yesterday), date.format(timeFormatter))
            0L -> String.format(stringProvider.getString(R.string.date_format_today), date.format(timeFormatter))
            1L -> String.format(stringProvider.getString(R.string.date_format_tomorrow), date.format(timeFormatter))
            2L -> stringProvider.getString(R.string.date_format_in_two_days)
            3L -> stringProvider.getString(R.string.date_format_in_three_days)
            else -> date.format(dateFormatter)
        }
    }

}