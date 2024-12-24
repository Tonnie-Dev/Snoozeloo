package com.tonyxlab.domain.usecases

import com.tonyxlab.utils.now
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.time.Duration.Companion.minutes

class GetSecsToNextAlarmUseCase {

    operator fun invoke(futureDate: LocalDateTime) = flow {

        while (true) {

            val now = LocalDateTime.now()
            val secs = ChronoUnit.SECONDS.between(
                    now.toJavaLocalDateTime(),
                    futureDate.toJavaLocalDateTime()
            )
            emit(secs)

            delay(1.minutes)
        }
    }
}