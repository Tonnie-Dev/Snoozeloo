package com.tonyxlab.domain.usecases

import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.domain.repository.AlarmRepository
import com.tonyxlab.utils.Resource
import javax.inject.Inject

class CreateAlarmUseCase @Inject constructor(private val repository: AlarmRepository) {

    suspend operator fun invoke(alarmItem: AlarmItem):Resource<Boolean>{

        return repository.createAlarm(alarmItem)
    }
}