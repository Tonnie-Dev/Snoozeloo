package com.tonyxlab.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.domain.usecases.GetAlarmsUseCase
import com.tonyxlab.domain.usecases.GetFutureDateUseCase
import com.tonyxlab.domain.usecases.GetSecsToNextAlarmUseCase
import com.tonyxlab.domain.usecases.GetSleepTimeInSecsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    getAlarmsUseCase: GetAlarmsUseCase,
    getSecsToNextAlarmUseCase: GetSecsToNextAlarmUseCase,
    getSleepTimeInSecsUseCase: GetSleepTimeInSecsUseCase,
    getFutureDateUseCase: GetFutureDateUseCase,

    ) : ViewModel() {

    private val _alarms = MutableStateFlow<List<AlarmState>>(emptyList())
    val alarms = _alarms.asStateFlow()

    init {

        getAlarmsUseCase().flatMapLatest { alarms ->

Timber.i("Is Alarms Empty Check 1: ${alarms.isEmpty()}")
                val alarmsFlow = alarms.sortedWith(
                        compareBy<AlarmItem> { it.triggerTime.hour }.thenBy { it.triggerTime.minute }
                )
                        .map { alarm ->


                            val futureDate =
                                getFutureDateUseCase(alarm.triggerTime, alarm.daysActive)
                            val secsLeftFlow = getSecsToNextAlarmUseCase(futureDate)
                            val sleepTimeFlow = getSleepTimeInSecsUseCase(futureDate)

                            combine(secsLeftFlow, sleepTimeFlow) { secsLeft, sleepTime ->
                               Timber.i("Secs Left: $secsLeft")
                                AlarmState(
                                        alarmItem = alarm,
                                        remainingSecs = secsLeft,
                                        sleepTime = sleepTime
                                )
                            }



                        }
            Timber.i("Exiting mapping")
            Timber.i("Alarm Flow Empty: - ${alarmsFlow.isEmpty()}")
                combine(alarmsFlow) {
                    Timber.i("Is Alarms Empty Check 2: ${it.isEmpty()}")
                    it.toList()

                }


        }
                .onEach {

                    Timber.i("Is Alarms Empty Check 3: ${it.isEmpty()}")
                    _alarms.value = it

                }
                .launchIn(viewModelScope)

Timber.i("Exiting init")

    }
}
