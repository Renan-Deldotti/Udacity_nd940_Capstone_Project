package com.test.watched.ui.settings

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.watched.utils.Constants
import com.test.watched.utils.getAppSharedPreferences


class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    val isFilterMoviesEnabled: MutableLiveData<Boolean> = MutableLiveData()
    val isNotificationsAllowd: MutableLiveData<Boolean> = MutableLiveData()
    private var preferences: SharedPreferences? = null

    init {
        preferences = application.getAppSharedPreferences()
        isFilterMoviesEnabled.value = preferences?.getBoolean(Constants.FILTER_ADULT_MOVIES_PREF_KEY, true)
        isNotificationsAllowd.value = preferences?.getBoolean(Constants.ALLOW_NOTIFICATIONS_PREF_KEY, true)
    }

    override fun onCleared() {
        super.onCleared()
        preferences?.let {
            Log.d("SettingsViewModel", "onCleared: saving preferences")
            val editor = it.edit()
            editor.putBoolean(Constants.FILTER_ADULT_MOVIES_PREF_KEY, isFilterMoviesEnabled.value ?: true)
            editor.putBoolean(Constants.ALLOW_NOTIFICATIONS_PREF_KEY, isNotificationsAllowd.value ?: true)
            editor.apply()
        }
    }
}