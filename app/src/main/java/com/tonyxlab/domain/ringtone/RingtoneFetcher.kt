package com.tonyxlab.domain.ringtone

import com.tonyxlab.domain.model.Ringtone
import kotlinx.coroutines.flow.Flow

interface RingtoneFetcher {

    fun fetchRingtone():Flow<MutableList<Ringtone>>
}