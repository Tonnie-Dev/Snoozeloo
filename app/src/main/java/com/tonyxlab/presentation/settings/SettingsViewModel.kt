package com.tonyxlab.presentation.settings

import androidx.lifecycle.ViewModel
import com.tonyxlab.domain.usecases.PickRingtoneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(

    private val ringtoneUseCase: PickRingtoneUseCase
) : ViewModel() {

}



