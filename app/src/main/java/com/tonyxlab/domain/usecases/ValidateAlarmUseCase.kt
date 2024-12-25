package com.tonyxlab.domain.usecases

import com.tonyxlab.utils.Resource
import javax.inject.Inject

class ValidateAlarmUseCase @Inject constructor() {


    operator fun invoke(hourString: String, minuteString: String): Resource<Boolean> {


        val hourInt = hourString.toIntOrNull()
            ?: return Resource.Error(Exception(message = "Hour should be a number"))

        val minuteInt = minuteString.toIntOrNull()
            ?: return Resource.Error(Exception(message = "Minute should be a number"))

        if ((hourInt in 0..23).not()) {
            return Resource.Error(Exception(message = "Hour should be between 0 and 23"))

        }

        if ((minuteInt in 0..59).not()) {
            return Resource.Error(Exception(message = "Minute should be between 0 and 59"))
        }

        return Resource.Success(true)

    }

}