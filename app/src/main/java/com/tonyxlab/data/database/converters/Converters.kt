package com.tonyxlab.data.database.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.tonyxlab.domain.json.JsonSerializer
import com.tonyxlab.domain.model.DayActivityState
import com.tonyxlab.domain.model.Ringtone
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import javax.inject.Inject

@ProvidedTypeConverter
class Converters @Inject constructor(
    private val serializer: JsonSerializer
) {

    private val chipsListSerializer: KSerializer<List<DayActivityState>> =
        ListSerializer(DayActivityState.serializer())

    private val ringtoneSerializer: KSerializer<Ringtone> = Ringtone.serializer()


    @TypeConverter
    fun writeDayChipStateList(list: List<DayActivityState>): String {

        return serializer.toJson(chipsListSerializer, list)
    }

    @TypeConverter
    fun readDayChipStateList(json: String): List<DayActivityState> {

        return serializer.fromJson(chipsListSerializer, json)
    }

    @TypeConverter
    fun writeRingtone(ringtone: Ringtone): String {

        return serializer.toJson(ringtoneSerializer, ringtone)
    }

    @TypeConverter
    fun readRingtone(json: String): Ringtone {

        return serializer.fromJson(ringtoneSerializer, json)
    }

}