package com.tonyxlab.data.database.converters


import com.tonyxlab.domain.json.Serializer
import com.tonyxlab.domain.model.DayChipState
import kotlinx.serialization.KSerializer
import javax.inject.Inject

class DayChipStateSerializer @Inject constructor(
    private val serializer: KSerializer<List<DayChipState>>
) : Serializer {

    fun toJson(data: List<DayChipState>): String {
        return toJson(serializer, data)
    }

    fun fromJson(json: String): List<DayChipState> {
        return fromJson(serializer, json)
    }
}