package com.example.fitness_routine.presentation.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class Calendar {

    @RequiresApi(Build.VERSION_CODES.O)
    fun createCalendar(startYear: Int, endYear: Int): CustomCalendar {
        val years = mutableListOf<Year>()
        val months = mutableListOf<Month>()

        for (year in startYear..endYear) {

            for (month in 1..12) {
                val monthName = when (month) {
                    1 -> "January"
                    2 -> "February"
                    3 -> "March"
                    4 -> "April"
                    5 -> "May"
                    6 -> "June"
                    7 -> "July"
                    8 -> "August"
                    9 -> "September"
                    10 -> "October"
                    11 -> "November"
                    12 -> "December"
                    else -> "Unknown"
                }

                val daysInMonth = when (month) {
                    1, 3, 5, 7, 8, 10, 12 -> 31
                    4, 6, 9, 11 -> 30
                    2 -> if (isLeapYear(year)) 29 else 28
                    else -> 0
                }

                val days = (1..daysInMonth).map { day ->
                    val localDate = LocalDate.of(year, month, day)
                    val dayOfWeekName = localDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
                    Day(day, dayOfWeekName)
                }

                months.add(Month(monthName, days))
            }

            years.add(Year(year, months))
        }

        return CustomCalendar(years)
    }

    private fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }


}


data class Day(val dayOfMonth: Int, val dayOfWeekName: String)

data class Month(val monthName: String, val days: List<Day>)

data class Year(val year: Int, val months: List<Month>)

data class CustomCalendar(val years: List<Year>)

