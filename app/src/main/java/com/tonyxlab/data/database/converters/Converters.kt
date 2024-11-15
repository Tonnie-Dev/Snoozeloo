package com.tonyxlab.data.database.converters

import android.net.Uri
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.tonyxlab.domain.json.JsonSerializer
import com.tonyxlab.domain.model.DayChipState
import com.tonyxlab.utils.fromLocalDateTimeToUtcMillis
import com.tonyxlab.utils.fromUtcMillisToLocalDateTimeDefault
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import javax.inject.Inject

@ProvidedTypeConverter
class Converters @Inject constructor(
    private val serializer: JsonSerializer
) {

    private val listSerializer: KSerializer<List<DayChipState>> =
        ListSerializer(DayChipState.serializer())

    @TypeConverter
    fun writeLocalDateTimeToLong(time: LocalDateTime?): Long? {

        return time?.fromLocalDateTimeToUtcMillis()
    }

    @TypeConverter
    fun readMillisToLocalDateTimeDefault(value: Long?): LocalDateTime? {

        return value?.fromUtcMillisToLocalDateTimeDefault()
    }

    @TypeConverter

    fun writeDayChipStateList(list: List<DayChipState>): String {

        return serializer.toJson(listSerializer, list)
    }

    @TypeConverter
    fun readDayChipStateList(json: String): List<DayChipState> {

        return serializer.fromJson(listSerializer, json)
    }

    @TypeConverter
    fun writeUri(uri: Uri?): String? {
        return uri?.toString()
    }

    @TypeConverter
    fun readUri(uriString: String?): Uri? {
        return uriString?.let { Uri.parse(it) }
    }
}