package com.tonyxlab.data.database.converters

import androidx.room.TypeConverter
import com.tonyxlab.domain.model.DayChipState
import com.tonyxlab.utils.fromLocalDateTimeToUtcMillis
import com.tonyxlab.utils.fromUtcMillisToLocalDateTimeDefault
import kotlinx.datetime.LocalDateTime


object Converters {

    @TypeConverter
    fun writeLocalDateTimeToLong(time: LocalDateTime?):Long? {

        return time?.fromLocalDateTimeToUtcMillis()
    }


    @TypeConverter
    fun readMillisToLocalDateTimeDefault (value:Long?):LocalDateTime? {

       return value?.fromUtcMillisToLocalDateTimeDefault()
    }

    @TypeConverter

    fun writeDayChipStateList(list:List<DayChipState>):String {

        return list.
    }
}