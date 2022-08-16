package com.test.watched

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.test.watched.data.datamodels.ShortMovieInfo
import com.test.watched.data.retrofit.RetrofitInstance
import com.test.watched.databinding.ActivityMainBinding
import com.test.watched.notifications.WatchedNotificationManager
import com.test.watched.utils.hasInternetConnectivity

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var configuration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setSupportActionBar(binding.activityMainToolbar)

        setContentView(binding.root)

        navController = findNavController(R.id.fragmentNavHost)
        configuration = AppBarConfiguration(
            setOf(R.id.nav_movies, R.id.nav_favorites, R.id.nav_settings, R.id.nav_about),
            binding.drawerLayout)
        setupActionBarWithNavController(navController, configuration)
        binding.navigationView.setupWithNavController(navController)

        if (!hasInternetConnectivity()) {
            // Show a snackbar about the connectivity
            Snackbar.make(binding.mainCoordinatorLayout, R.string.no_internet_connection, Snackbar.LENGTH_LONG)
                .setAction(R.string.turn_on){
                    startActivity(Intent().apply {
                        action = Settings.ACTION_WIFI_SETTINGS
                    })
                }.show()
        }

        val id: Int? = intent.extras?.getInt(WatchedNotificationManager.NOTIFICATION_MOVIE_ID_EXTRA, -1)
        id?.let {
            if (hasInternetConnectivity() && it != -1) {
                navController.navigate(NavigationDirections.fromNotificationToNavDetails(it))
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(configuration) || super.onSupportNavigateUp()
    }
}