package com.tonyxlab.domain.ringtone

import android.net.Uri
import com.tonyxlab.domain.model.Ringtone
import kotlinx.coroutines.flow.Flow

interface RingtoneFetcher {

    fun fetchRingtone():Flow<MutableList<Ringtone>>
    fun startPlay(uri:Uri)
    fun stopPlay()
    fun release()

}