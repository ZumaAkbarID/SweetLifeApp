package com.amikom.sweetlife.util

import android.os.Build
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun formatDateTime(dateTimeString: String): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Menggunakan API 26 atau lebih baru
        val inputFormatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val dateTime = java.time.LocalDateTime.parse(dateTimeString, inputFormatter)

        val dayOfWeek = dateTime.dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, Locale.ENGLISH)
        val month = dateTime.month.getDisplayName(java.time.format.TextStyle.FULL, Locale.ENGLISH)
        val dayOfMonth = dateTime.dayOfMonth
        val year = dateTime.year
        val hour = dateTime.hour
        val minute = dateTime.minute
        val second = dateTime.second

        "$dayOfWeek, $dayOfMonth $month $year at $hour:$minute:$second"
    } else {
        // Menggunakan API di bawah 26
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            val outputFormat = SimpleDateFormat("EEEE, d MMMM yyyy 'at' HH:mm:ss", Locale.ENGLISH)

            val date = inputFormat.parse(dateTimeString) ?: return "Invalid date format"
            outputFormat.format(date)
        } catch (e: Exception) {
            "Invalid date format"
        }
    }
}