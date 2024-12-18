package com.tonyxlab.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone


import kotlin.test.Test
import kotlin.test.assertEquals

class DateUtilsKtTest {
// write a test for fromLocalDateTimeToMillis for TimeZone.currentSystemDefault() and UTC Time

@Test
fun `Test from_LocalDate_Time_To_Millis function when system default time is passed`() {
    val utcTimeStamp =1734501000000 // 18 Dec 2024 05:50:00
    val time = LocalDateTime(
            year = 2024,
            monthNumber = 12,
            dayOfMonth = 18,
            hour = 5,
            minute = 50,
            second = 0
    )
    assertEquals(expected = utcTimeStamp, actual = time.fromLocalDateTimeToMillis(timeZone = TimeZone.UTC))
}

    @Test
    fun `Test get_Hour_String function`(){


        val time1= LocalDateTime(2021, 10, 10, 8, 32)
        val time2= LocalDateTime(2021, 10, 10, 11, 1)
        assertEquals(expected = "08", actual = time1.getHourString())
        assertEquals(expected = "11", actual = time2.getHourString())
    }

    @Test
    fun `Test get_Minute_String function`(){

        val time1= LocalDateTime(2021, 10, 10, 10, 32)
        val time2= LocalDateTime(2021, 10, 10, 9, 1)
        val time3= LocalDateTime(2021, 10, 10, 9, 0)


        assertEquals(expected = "32", actual = time1.getMinuteString())
        assertEquals(expected = "01", actual = time2.getMinuteString())
        assertEquals(expected = "00", actual = time3.getMinuteString())
    }
}