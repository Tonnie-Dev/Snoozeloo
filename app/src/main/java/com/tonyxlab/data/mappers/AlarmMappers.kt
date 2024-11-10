package com.tonyxlab.data.mappers

import com.tonyxlab.data.database.entity.AlarmEntity

fun com.tonyxlab.domain.model.AlarmItem.toEntityModel(): AlarmEntity {

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

fun AlarmEntity.toDomainModel(): com.tonyxlab.domain.model.AlarmItem {

    return com.tonyxlab.domain.model.AlarmEntity(
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