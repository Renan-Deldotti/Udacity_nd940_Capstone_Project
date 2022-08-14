package com.test.watched

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.test.watched.data.retrofit.RetrofitInstance

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launchWhenCreated { tryit() }
    }

    suspend fun tryit() {
        val t = RetrofitInstance.api.getPopularMovies(page = "2")
        Log.d(TAG, "tryit: $t")

        val u = RetrofitInstance.api.getMovieById("616037")
        Log.d(TAG, "tryit: $u")
    }
}