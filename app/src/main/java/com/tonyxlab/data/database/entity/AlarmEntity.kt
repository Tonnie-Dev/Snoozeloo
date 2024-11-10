package com.tonyxlab.data.database.entity

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tonyxlab.domain.model.DayActive
import java.time.LocalDateTime

@Entity(tableName = "alarms_table")
data class AlarmEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "is_enabled")
    val isEnabled: Boolean,
    @ColumnInfo(name = "trigger_time")
    val triggerTime: LocalDateTime,
    @ColumnInfo(name = "duration_to_next_trigger")
    val durationToNextTrigger: Long,
    @ColumnInfo(name = "days_active")
    val daysActive: List<DayActive> = emptyList(),
    @ColumnInfo(name = "ringtone")
    val ringtone: Uri? = null,
    @ColumnInfo(name = "volume")
    val volume: Int = 0,
    @ColumnInfo(name = "is_haptic_on")
    val isHapticOn: Boolean = false,
    @ColumnInfo(name = "wake_up_time")
    val wakeUpTime: String? = null
)
