package com.tonyxlab.domain.usecases

import android.net.Uri
import com.tonyxlab.domain.repository.AlarmRepository
import com.tonyxlab.domain.ringtone.RingtonePicker
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PickRingtoneUseCase @Inject constructor(
    private val picker: RingtonePicker,
    private val repository: AlarmRepository
) {
    val ringtoneFlow: Flow<Uri?> = repository.ringtoneFlow

    suspend fun selectAndSaveRingtone() {
        val selectedRingtone = picker.pickRingtone()
        if (selectedRingtone != null) {
            repository.saveRingtone(selectedRingtone)
        }
    }
}
