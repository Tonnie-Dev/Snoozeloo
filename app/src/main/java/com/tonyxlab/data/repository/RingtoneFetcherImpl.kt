package com.tonyxlab.data.repository

import android.content.Context
import android.media.RingtoneManager
import com.tonyxlab.domain.model.Ringtone
import com.tonyxlab.domain.ringtone.RingtoneFetcher
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RingtoneFetcherImpl @Inject constructor(
    @ApplicationContext private val context: Context
) :
    RingtoneFetcher {

    override fun fetchRingtone(): Flow<MutableList<Ringtone>> = flow {


        val ringtoneManager = RingtoneManager(context).apply {

            setType(RingtoneManager.TYPE_ALARM)


        }

        val cursor = ringtoneManager.cursor
        val ringtones = mutableListOf<Ringtone>()

        while (cursor.moveToNext()) {

            val ringtone = ringtoneManager.getRingtone(cursor.position)
                    .getTitle(context)
            val ringtoneUri = ringtoneManager.getRingtoneUri(cursor.position)

            ringtones.add(
                    Ringtone(
                            ringtoneName = ringtone,
                            ringtoneUri = ringtoneUri
                    )
            )
        }
        cursor.close()

        emit(ringtones)
    }
}