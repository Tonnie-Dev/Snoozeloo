package com.tonyxlab.domain.json

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

interface Serializer {
    fun <T> toJson(serializer: KSerializer<T>, data: T): String {
        return Json.encodeToString(serializer, data)
    }

    fun <T> fromJson(serializer: KSerializer<T>, json: String): T {
        return Json.decodeFromString(serializer, json)
    }
}
