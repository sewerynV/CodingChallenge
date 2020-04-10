package com.seweryn.codechallenge.utils

import org.threeten.bp.LocalDateTime

interface DateFormatter {
    fun formatDateForDisplay(date: LocalDateTime): String
}