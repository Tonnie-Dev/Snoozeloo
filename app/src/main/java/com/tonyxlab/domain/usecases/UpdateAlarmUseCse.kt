package com.tonyxlab.domain.usecases

import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.domain.repository.AlarmRepository
import com.tonyxlab.utils.Resource
import javax.inject.Inject

class UpdateAlarmUseCse @Inject constructor(private val repository: AlarmRepository){
    suspend operator fun invoke(alarmItem: AlarmItem):Resource<Boolean> {

        if (alarmItem.id.isBlank()){

            return  Resource.Error(Exception("Error, Please provide a valid alarm item"))
        }
        return repository.updateAlarm(alarmItem)
    }
}