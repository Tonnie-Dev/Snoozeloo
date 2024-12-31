package com.tonyxlab.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.domain.model.DayActivityState
import com.tonyxlab.domain.usecases.GetAlarmByIdUseCase
import com.tonyxlab.domain.usecases.GetAlarmsUseCase
import com.tonyxlab.domain.usecases.GetFutureDateUseCase
import com.tonyxlab.domain.usecases.GetSecsToNextAlarmUseCase
import com.tonyxlab.domain.usecases.GetSleepTimeInSecsUseCase
import com.tonyxlab.domain.usecases.UpdateActiveDaysUseCase
import com.tonyxlab.domain.usecases.UpdateAlarmUseCase
import com.tonyxlab.utils.AppCoroutineDispatchers
import com.tonyxlab.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    getAlarmsUseCase: GetAlarmsUseCase,
    getSecsToNextAlarmUseCase: GetSecsToNextAlarmUseCase,
    getSleepTimeInSecsUseCase: GetSleepTimeInSecsUseCase,
    getFutureDateUseCase: GetFutureDateUseCase,
    private val getAlarmByIdUseCase: GetAlarmByIdUseCase,
    private val updateActiveDaysUseCase: UpdateActiveDaysUseCase,
    private val updateAlarmUseCase: UpdateAlarmUseCase,
    private val dispatchers: AppCoroutineDispatchers,

    ) : ViewModel() {

    private val _alarms = MutableStateFlow<List<AlarmState>>(emptyList())
    val alarms = _alarms.asStateFlow()

    private val activeDays = MutableStateFlow<List<DayActivityState>>(emptyList())

    init {

        getAlarmsUseCase().flatMapLatest { alarms ->

            val alarmsFlow = alarms.sortedWith(
                    compareBy<AlarmItem> { it.triggerTime.hour }.thenBy { it.triggerTime.minute }
            )
                    .map { alarm ->

                        val futureDate =
                            getFutureDateUseCase(alarm.triggerTime, alarm.daysActive)
                        val secsLeftFlow = getSecsToNextAlarmUseCase(futureDate)
                        val sleepTimeFlow = getSleepTimeInSecsUseCase(futureDate)

                        combine(secsLeftFlow, sleepTimeFlow) { secsLeft, sleepTime ->
                            AlarmState(
                                    alarmItem = alarm,
                                    remainingSecs = secsLeft,
                                    sleepTime = sleepTime
                            )
                        }
                    }

            combine(alarmsFlow) {

                it.toList()

            }

        }
                .onEach {

                    _alarms.value = it

                }
                .launchIn(viewModelScope)

    }

    private suspend fun updateAlarmItem(alarmItem: AlarmItem) {

        updateAlarmUseCase(alarmItem = alarmItem)
    }


    private suspend fun getInitialActiveDays(alarmItem: AlarmItem) =

        withContext(dispatchers.io) {

            when (val result = getAlarmByIdUseCase(alarmItem.id)) {

                is Resource.Success -> {

                    activeDays.update { result.data.daysActive }
                }

                else -> Unit
            }

        }


    private fun updateActiveDays(clickedIndex: Int) {

        this.activeDays.update {
            updateActiveDaysUseCase(activeDays = this.activeDays.value, clickedIndex = clickedIndex)
        }

    }

    fun onDayAlarmActivityChange(
        alarmItem: AlarmItem, clickedIndex: Int,
    ) {
        viewModelScope.launch {
            getInitialActiveDays(alarmItem = alarmItem)
            updateActiveDays(clickedIndex = clickedIndex)
            updateAlarmItem(alarmItem = alarmItem.copy(daysActive = this@HomeViewModel.activeDays.value))
        }

    }
}
