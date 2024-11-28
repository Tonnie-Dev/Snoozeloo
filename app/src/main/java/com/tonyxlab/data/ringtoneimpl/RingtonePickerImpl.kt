package com.tonyxlab.data.ringtoneimpl

import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts

import com.tonyxlab.domain.ringtone.RingtonePicker
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RingtonePickerImpl @Inject constructor(
   activity: ComponentActivity
) : RingtonePicker {
    private val ringtonePickerLauncher = activity.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
        val selectedRingtoneUri: Uri? = result.data?.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
        ringtoneResultFlow.value = selectedRingtoneUri
    }

    private val ringtoneResultFlow = MutableStateFlow<Uri?>(null)

    override suspend fun pickRingtone(): Uri? {
        val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER).apply {
            putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Alarm Ringtone")
            putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM)
            putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, true)

        }
        ringtonePickerLauncher.launch(intent)

        // Wait until a result is emitted
        return ringtoneResultFlow.filterNotNull().first()
    }
}
