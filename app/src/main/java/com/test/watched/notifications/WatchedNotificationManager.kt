package com.test.watched.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.test.watched.MainActivity
import com.test.watched.R
import com.test.watched.utils.Constants
import com.test.watched.utils.getAppSharedPreferences
import com.test.watched.utils.getNotificationManager

class WatchedNotificationManager(private val context: Context) {

    init {
        initializeChannels()
    }

    private fun initializeChannels() {
        val newsChannel = NotificationChannel(
            NEWS_CHANNEL_ID,
            NEWS_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        context.getNotificationManager().createNotificationChannel(newsChannel)
    }

    fun createNewsNotification(movieId: Int?, movieTitle: String?) {
        val isNotificationsEnabled = context.getAppSharedPreferences().getBoolean(Constants.ALLOW_NOTIFICATIONS_PREF_KEY, true)
        if (!isNotificationsEnabled) {
            Log.d(TAG, "createNewsNotification: Notifications are disabled")
            return
        }
        val notificationIntent = Intent(Intent.ACTION_MAIN, null).apply {
            flags = Intent.FLAG_ACTIVITY_NO_USER_ACTION or Intent.FLAG_ACTIVITY_NEW_TASK
            setClass(context, MainActivity::class.java)
            putExtra(NOTIFICATION_MOVIE_ID_EXTRA, movieId!!)
        }

        val pendingIntent = PendingIntent.getActivity(context, NEWS_REQUEST_CODE, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        val notification = Notification.Builder(context, NEWS_CHANNEL_ID)
            .setCategory(Notification.CATEGORY_SOCIAL)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(movieTitle)
            .build()
        context.getNotificationManager().notify(NEWS_CHANNEL_ID, NEWS_NOTIFICATION_ID, notification)
    }

    companion object {
        private const val TAG = "WatchedNotificationMana"
        private const val NEWS_CHANNEL_ID = "WATCHED::NEWS_CHANNEL_ID"
        private const val NEWS_CHANNEL_NAME = "WATCHED_NEWS_CHANNEL"
        private const val NEWS_NOTIFICATION_ID: Int = 1001
        private const val NEWS_REQUEST_CODE: Int = 1002
        const val NOTIFICATION_MOVIE_ID_EXTRA: String = "NOTIFICATION_MOVIE_ID_EXTRA_value"
    }
}