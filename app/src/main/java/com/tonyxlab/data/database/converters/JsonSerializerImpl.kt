package com.tonyxlab.data.database.converters


import com.tonyxlab.domain.json.JsonSerializer
import kotlinx.serialization.KSerializer
import javax.inject.Inject
import kotlinx.serialization.json.Json
class JsonSerializerImpl<T> @Inject constructor(
    private val serializer: KSerializer<T>
) : JsonSerializer {

    override fun <T> toJson(serializer: KSerializer<T>, data: T): String {
        return Json.encodeToString(serializer, data)
    }

    override fun <T> fromJson(serializer: KSerializer<T>, json: String): T {
        return Json.decodeFromString(serializer, json)
    }
}