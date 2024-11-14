package com.tonyxlab.data.database.converters


import com.tonyxlab.domain.json.JsonSerializer
import kotlinx.serialization.KSerializer
import javax.inject.Inject

class JsonSerializerImpl<T> @Inject constructor(
    private val serializer: KSerializer<T>
) : JsonSerializer {

    fun toJson(data: T): String {
        return toJson(serializer, data)
    }

    fun fromJson(json: String): T {
        return fromJson(serializer, json)
    }
}