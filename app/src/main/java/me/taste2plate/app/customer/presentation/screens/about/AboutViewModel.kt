package me.taste2plate.app.customer.presentation.screens.about

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.taste2plate.app.customer.data.UserPref
import javax.inject.Inject


@HiltViewModel
class AboutViewModel @Inject constructor(
    var userPref: UserPref
) : ViewModel() {
    var state by mutableStateOf(AboutState())

    fun getData() {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            val setting = userPref.getSettings()
            state = state.copy(
                setting = setting,
                isLoading = false,
                isError = setting == null
            )
        }
    }
}

