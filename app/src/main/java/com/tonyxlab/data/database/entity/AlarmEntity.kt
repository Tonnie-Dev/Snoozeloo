package com.tonyxlab.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tonyxlab.domain.model.DayChipState
import com.tonyxlab.domain.model.Ringtone
import com.tonyxlab.domain.model.SILENT_RINGTONE


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
    val triggerTime: Long,
    @ColumnInfo(name = "days_active")
    val daysActive: List<DayChipState> = emptyList(),
    @ColumnInfo(name = "ringtone")
    val ringtone: Ringtone = SILENT_RINGTONE,
    @ColumnInfo(name = "volume")
    val volume: Float = 0f,
    @ColumnInfo(name = "is_haptic_on")
    val isHapticsOn: Boolean = false,
    @ColumnInfo(name = "wake_up_time")
    val wakeUpTime: String? = null
)
