package com.example.fitness_routine.presentation.util

import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

class Calendar {

    fun createCalendar(startYear: Int, endYear: Int): CustomCalendar {
        val years = mutableListOf<Year>()
        val months = mutableListOf<Month>()

        for (year in startYear..endYear) {
            for (month in 1..12) {
                val monthName = getMonthName(month)
                val daysInMonth = getMonthDays(month, year)
                val days = getDays(daysInMonth, year, month)

                months.add(Month(monthName, days))
            }
            years.add(Year(year, months))
        }

        return CustomCalendar(years)
    }

    private fun getDays(
        daysInMonth: Int,
        year: Int,
        month: Int
    ) = (1..daysInMonth).map { day ->
        val localDate = LocalDate.of(year, month, day)
        val dayOfWeekName = localDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
        Day(day, dayOfWeekName, createDate(day, month, year))
    }


    private fun getMonthDays(month: Int, year: Int) = when (month) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        2 -> if (isLeapYear(year)) 29 else 28
        else -> 0
    }

    private fun getMonthName(month: Int) = when (month) {
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


    private fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }
}


fun getCurrentDay(): String = LocalDate.now().dayOfMonth.toString()

fun getCurrentMonth(): String = LocalDate.now().month.getDisplayName(TextStyle.FULL, Locale.getDefault())

fun getCurrentYear(): String = LocalDate.now().year.toString()

fun getDate(): String {
    val currentDate = LocalDate.now()

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return currentDate.format(formatter)
}


fun createDate(day: Int, month: Int, year: Int): Long {
    val localDate = LocalDate.of(year, month, day)
    val instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
    return Date.from(instant).toTimeStamp()
}

fun getCurrentDate(): Long {
    val localDate = LocalDate.now()
    val instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
    return Date.from(instant).toTimeStamp()
}


data class Day(val dayOfMonth: Int, val dayOfWeekName: String, val date: Long)

data class Month(val monthName: String, val days: List<Day>)

data class Year(val year: Int, val months: List<Month>)

data class CustomCalendar(val years: List<Year>)



