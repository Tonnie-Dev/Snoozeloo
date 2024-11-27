package com.tonyxlab.domain.ringtone

import android.net.Uri

interface RingtonePicker {
    suspend fun pickRingtone(): Uri?
}
