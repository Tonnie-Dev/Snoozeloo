package com.tonyxlab.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlin.math.min


import kotlin.test.Test
import kotlin.test.assertEquals

class DateUtilsKtTest {

    //setTriggerTime

    @Test
    fun `Test set_Trigger_Time` () {
       val hour = 7
       val minute = 39
      val dateTime = setTriggerTime(hour = hour, minute = minute)
       assertEquals(expected =7, actual = dateTime.hour)
    }

    @Test
    fun `Test from_LocalDate_Time_To_Millis function when system default time is passed`() {

        val time = LocalDateTime(
                year = 2024,
                monthNumber = 12,
                dayOfMonth = 19,
                hour = 8,
                minute = 15,
                second = 0
        )

        val utcTimeStamp = 1734596100000
        val defaultTimeStamp = 1734585300000

        assertEquals(
                expected = utcTimeStamp,
                actual = time.fromLocalDateTimeToMillis(timeZone = TimeZone.UTC)
        )
        assertEquals(
                expected = defaultTimeStamp,
                actual = time.fromLocalDateTimeToMillis(timeZone = TimeZone.currentSystemDefault())
        )
    }


    @Test

    fun `Test from_Local_Date_Time_To_Utc_Time_Stamp function`() {


        val time = LocalDateTime(
                year = 2024,
                monthNumber = 12,
                dayOfMonth = 19,
                hour = 8,
                minute = 15,
                second = 0
        )

        val utcTimeStamp = 1734596100000
/*
        assertEquals(expected = utcTimeStamp, actual = time.fromLocalDateTimeToUtcTimeStamp())
*/
    }

    @Test
    fun `Test get_Hour_String function`() {


        val time1 = LocalDateTime(2021, 10, 10, 8, 32)
        val time2 = LocalDateTime(2021, 10, 10, 11, 1)
        assertEquals(expected = "08", actual = time1.getHourString())
        assertEquals(expected = "11", actual = time2.getHourString())
    }

    @Test
    fun `Test get_Minute_String function`() {

        val time1 = LocalDateTime(2021, 10, 10, 10, 32)
        val time2 = LocalDateTime(2021, 10, 10, 9, 1)
        val time3 = LocalDateTime(2021, 10, 10, 9, 0)


        assertEquals(expected = "32", actual = time1.getMinuteString())
        assertEquals(expected = "01", actual = time2.getMinuteString())
        assertEquals(expected = "00", actual = time3.getMinuteString())
    }
}