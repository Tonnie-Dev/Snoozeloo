package com.tonyxlab.domain.usecases

import com.tonyxlab.domain.model.DayActivityState
import javax.inject.Inject

class UpdateActiveDaysUseCase @Inject constructor() {


    operator fun invoke(
        activeDays: List<DayActivityState>,
        clickedIndex: Int
    ): List<DayActivityState> {

        val daysActive = activeDays.mapIndexed { i, chipState ->


            if (i == clickedIndex) chipState.copy(isEnabled = !chipState.isEnabled) else chipState
        }

        return daysActive
    }
}