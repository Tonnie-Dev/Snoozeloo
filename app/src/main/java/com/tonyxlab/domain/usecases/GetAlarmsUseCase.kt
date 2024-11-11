package com.tonyxlab.domain.usecases

import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlarmsUseCase @Inject constructor(private val repository: AlarmRepository) {

    operator fun invoke():Flow<List<AlarmItem>>{

        return repository.getAlarms()
    }
}