package com.tonyxlab.data.mappers

import com.tonyxlab.data.database.entity.AlarmEntity
import com.tonyxlab.domain.model.AlarmItem

fun AlarmItem.toEntityModel(): AlarmEntity {

    return AlarmEntity(
            id = id,
            name = name,
            isEnabled = isEnabled,
            triggerTime = triggerTime,
            durationToNextTrigger = durationToNextTrigger,
            daysActive = daysActive,
            ringtone = ringtone,
            volume = volume,
            isHapticsOn = isHapticsOn,
            wakeUpTime = wakeUpTime
    )
}

fun AlarmEntity.toDomainModel():AlarmItem {

    return AlarmItem(
            id = id,
            name = name,
            isEnabled = isEnabled,
            triggerTime = triggerTime,
            durationToNextTrigger = durationToNextTrigger,
            daysActive = daysActive,
            ringtone = ringtone,
            volume = volume,
            isHapticsOn = isHapticsOn,
            wakeUpTime = wakeUpTime
    )
}