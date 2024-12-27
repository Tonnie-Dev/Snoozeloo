package com.tonyxlab.domain.model

import android.net.Uri
import com.tonyxlab.utils.now
import com.tonyxlab.utils.plusDays
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


data class AlarmItem(
    val id: String,
    val name: String,
    val isEnabled: Boolean,
    val triggerTime: LocalDateTime,

    val daysActive: List<DayChipState> = emptyList(),
    val ringtone: Ringtone = SILENT_RINGTONE,
    val volume: Float = 0f,
    val isHapticsOn: Boolean = false,
    val wakeUpTime: String? = null
)


@Serializable
data class DayChipState(val day: Int, val isEnabled: Boolean)

@Serializable
data class Ringtone(
    val ringtoneName: String,
    @Serializable(with = UriSerializer::class)
    val ringtoneUri: Uri
) {

    override fun toString(): String {
        return ringtoneName
    }
}

val SILENT_RINGTONE = Ringtone(ringtoneName = "Silent", ringtoneUri = Uri.EMPTY)

object UriSerializer : KSerializer<Uri> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Uri", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Uri) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): Uri {
        return Uri.parse(decoder.decodeString())
    }
}

