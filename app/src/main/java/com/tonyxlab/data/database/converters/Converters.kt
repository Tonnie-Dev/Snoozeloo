package com.tonyxlab.data.database.converters

import android.net.Uri
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.tonyxlab.domain.json.JsonSerializer
import com.tonyxlab.domain.model.DayChipState
import com.tonyxlab.domain.model.Ringtone
import com.tonyxlab.utils.fromLocalDateTimeToMillis
import com.tonyxlab.utils.fromLocalToUTCTimeStamp
import com.tonyxlab.utils.fromMillisToLocalDateTime
import com.tonyxlab.utils.fromUTCToLocalTimeStamp
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import javax.inject.Inject

@ProvidedTypeConverter
class Converters @Inject constructor(
    private val serializer: JsonSerializer
) {

    @TypeConverter
fun writeLocalDateTimeToLong(time: LocalDateTime?): Long? {

    return time?.fromLocalDateTimeToMillis()?.fromLocalToUTCTimeStamp()
}
    @TypeConverter
    fun readMillisToLocalDateTimeDefault(value: Long): LocalDateTime {

        return Instant.fromEpochMilliseconds(value).toLocalDateTime(TimeZone.currentSystemDefault())
    }

    @TypeConverter

    fun writeDayChipStateList(list: List<DayChipState>): String {

        return serializer.toJson(chipsListSerializer, list)
    }
    private val chipsListSerializer: KSerializer<List<DayChipState>> =
        ListSerializer(DayChipState.serializer())

    private val ringtoneSerializer:KSerializer<Ringtone> = Ringtone.serializer()





    @TypeConverter
    fun readDayChipStateList(json: String): List<DayChipState> {

        return serializer.fromJson(chipsListSerializer, json)
    }

    @TypeConverter
    fun writeRingtone(ringtone: Ringtone):String {

        return serializer.toJson(ringtoneSerializer, ringtone)
    }

    @TypeConverter
    fun readRingtone(json: String):Ringtone{

        return serializer.fromJson(ringtoneSerializer, json)
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