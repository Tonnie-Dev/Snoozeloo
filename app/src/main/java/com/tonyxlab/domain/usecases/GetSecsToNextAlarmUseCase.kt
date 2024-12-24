package com.tonyxlab.domain.usecases

import com.tonyxlab.utils.now
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class GetSecsToNextAlarmUseCase {

    operator fun invoke(
        futureDate: LocalDateTime,
        delay: Duration = 1.minutes
    ) = flow {

        while (true) {

            val now = LocalDateTime.now()
            val secs = ChronoUnit.SECONDS.between(
                    now.toJavaLocalDateTime(),
                    futureDate.toJavaLocalDateTime()
            )
            emit(secs)

            delay(delay)
        }
    }
}