package com.test.watched

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.test.watched.data.retrofit.RetrofitInstance
import com.test.watched.databinding.ActivityMainBinding

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

        lifecycleScope.launchWhenCreated { tryit() }
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(configuration) || super.onSupportNavigateUp()
    }

    suspend fun tryit() {
        val t = RetrofitInstance.api.getPopularMovies(page = "2")
        Log.d(TAG, "tryit: $t")

        val u = RetrofitInstance.api.getMovieById("616037")
        Log.d(TAG, "tryit: $u")
    }
}