package com.seweryn.dazncodechallenge.utils

import org.threeten.bp.Duration
import org.threeten.bp.LocalDate


class DateDifferenceUtil {
    companion object {
        fun dayDifferenceFromToday(date: LocalDate): Long {
            val today = LocalDate.now()
            return Duration.between(today.atStartOfDay(), date.atStartOfDay()).toDays()
        }
    }
}