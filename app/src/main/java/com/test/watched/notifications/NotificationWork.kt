package com.test.watched.notifications

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker.Result.retry
import androidx.work.ListenableWorker.Result.success
import androidx.work.WorkerParameters
import com.test.watched.data.datamodels.ShortMovieInfo
import com.test.watched.data.retrofit.RetrofitInstance
import java.lang.Exception

class NotificationWork(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {

        return try {
            val data = RetrofitInstance.api.getTrendingMovies().results
            if (data.isNotEmpty()) {
                val randomMovieId:Int = (0..data.size).random()
                val movie: ShortMovieInfo = data[randomMovieId]
                val movieId = movie.shortMovieId
                val movieTitle = movie.originalTitle

                WatchedNotificationManager(applicationContext).createNewsNotification(movieId, movieTitle)
            }
            success()
        } catch (e: Exception) {
            retry()
        }

    }

    companion object {
        const val WORK_NAME = "NotificationWork"
    }
}