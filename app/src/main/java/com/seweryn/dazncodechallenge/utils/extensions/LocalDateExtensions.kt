package com.seweryn.dazncodechallenge.utils.extensions

import org.threeten.bp.Duration
import org.threeten.bp.LocalDate

fun LocalDate.dayDifferenceFromToday(): Long {
    val today = LocalDate.now()
    return Duration.between(today.atStartOfDay(), this.atStartOfDay()).toDays()
}