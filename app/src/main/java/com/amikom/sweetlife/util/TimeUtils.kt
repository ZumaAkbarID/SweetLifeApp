package com.amikom.sweetlife.util

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
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

fun countAgeFromDate(bornDate: String): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val tanggalLahirDate = LocalDate.parse(bornDate, formatter)
        val today = LocalDate.now()
        val period = Period.between(tanggalLahirDate, today)
        period.years
    } else {
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val date = inputFormat.parse(bornDate) ?: return 0

            val calendar = Calendar.getInstance()
            calendar.time = date

            val birthYear = calendar.get(Calendar.YEAR)
            val birthMonth = calendar.get(Calendar.MONTH)
            val birthDay = calendar.get(Calendar.DAY_OF_MONTH)

            val today = Calendar.getInstance()
            val currentYear = today.get(Calendar.YEAR)
            val currentMonth = today.get(Calendar.MONTH)
            val currentDay = today.get(Calendar.DAY_OF_MONTH)

            var age = currentYear - birthYear

            if (currentMonth < birthMonth || (currentMonth == birthMonth && currentDay < birthDay)) {
                age--
            }

            age
        } catch (e: Exception) {
            0
        }
    }
}

fun getCurrentDate(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val currentDate = LocalDate.now()
        currentDate.format(formatter)
    } else {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val currentDate = Calendar.getInstance().time
        formatter.format(currentDate)
    }
}