package com.tonyxlab.data.mappers

import com.tonyxlab.data.database.entity.AlarmEntity
import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.utils.fromLocalToUtcTimeStamp
import com.tonyxlab.utils.fromMillisToLocalDateTime

fun AlarmItem.toEntityModel(): AlarmEntity {

    return AlarmEntity(
            id = id,
            name = name,
            isEnabled = isEnabled,
            triggerTime = triggerTime.fromLocalToUtcTimeStamp(),
            daysActive = daysActive,
            ringtone = ringtone,
            volume = volume,
            isHapticsOn = isHapticsOn,
            wakeUpTime = wakeUpTime
    )
}

fun AlarmEntity.toDomainModel(): AlarmItem {

    return AlarmItem(
            id = id,
            name = name,
            isEnabled = isEnabled,
            triggerTime = triggerTime.fromMillisToLocalDateTime(),
            daysActive = daysActive,
            ringtone = ringtone,
            volume = volume,
            isHapticsOn = isHapticsOn,
            wakeUpTime = wakeUpTime
    )
}