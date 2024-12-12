package com.tonyxlab.domain.usecases

import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.domain.repository.AlarmRepository
import com.tonyxlab.utils.Resource
import javax.inject.Inject

class GetAlarmByIdUseCase @Inject constructor(private val repository: AlarmRepository) {

    suspend operator fun invoke(alarmId: String?): Resource<AlarmItem> {

        if (alarmId.isNullOrBlank()) {

            return Resource.Error(Exception("Error, Please provide a valid alarm item"))
        }
        return repository.getAlarmById(alarmId)
    }
}