package com.test.watched.utils

import android.content.Context
import android.content.SharedPreferences

fun Context.getAppSharedPreferences(): SharedPreferences = getSharedPreferences(Constants.WATCHED_APP_SHARED_PREF_NAME, Context.MODE_PRIVATE)