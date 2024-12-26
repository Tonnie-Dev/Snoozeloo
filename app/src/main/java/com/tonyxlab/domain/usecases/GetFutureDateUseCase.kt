package com.tonyxlab.domain.usecases

import com.tonyxlab.domain.model.DayChipState
import com.tonyxlab.utils.now
import com.tonyxlab.utils.plusDays
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject

class GetFutureDateUseCase @Inject constructor() {

    operator fun invoke(alarmTriggerTime: LocalDateTime, list: List<DayChipState>): LocalDateTime {
        val finalDateTime: LocalDateTime?
        val currentTime = LocalDateTime.now()
        val dayOfWeek = currentTime.dayOfWeek.value

        val checkedDays = mutableListOf<Int>()

        for (i in list.indices) {


            if (list[i].isEnabled) {

                checkedDays.add(i)
            }
        }

        if (checkedDays.contains(dayOfWeek)) {
            finalDateTime = carryTime(alarmTriggerTime = alarmTriggerTime)


        } else if (checkedDays.isEmpty()) {

            finalDateTime = carryTime(alarmTriggerTime = alarmTriggerTime)
        } else {

            val firstDay = checkedDays.first()

            if (firstDay < dayOfWeek) {

                finalDateTime = alarmTriggerTime.plusDays(dayOfWeek - firstDay)
            } else {

                finalDateTime = alarmTriggerTime.plusDays(firstDay - dayOfWeek)
            }

        }

        return finalDateTime
    }

    private fun carryTime(alarmTriggerTime: LocalDateTime): LocalDateTime {

        val currentTime = LocalDateTime.now()
        return if (alarmTriggerTime < currentTime) {


            //earlier
            alarmTriggerTime.plusDays(1)
        } else {
            alarmTriggerTime
            //after

        }
    }

}
