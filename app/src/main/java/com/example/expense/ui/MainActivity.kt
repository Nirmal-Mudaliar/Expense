package com.example.expense.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.example.expense.R
import com.example.expense.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    // Setup view binding
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

         // setup theme

        val sp: SharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val appTheme: Int = sp.getInt("theme", -1)
        if (appTheme == -1) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
        else if (appTheme == 2) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

//        val appSettingPref: SharedPreferences = getSharedPreferences("AppSettingPrefs", 0 )
//        val appTheme: Int = appSettingPref.getInt("theme", 0)
//
//        if (appTheme == -1) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
//        }
//        else if (appTheme == 2) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        }
//        else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        }


//        val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
//        val isDarkMode = sp.getInt("AppTheme")
        //Log.d("is Dark mode: ", isDarkMode.toString() )

       // val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
//        val defaultValue = resources.getInteger(R.integer.saved_high_score_default_key)
//        val highScore = sharedPref.getInt(getString(R.string.saved_high_score_key), defaultValue)

        // Setup bottom navigation view
        val navView: BottomNavigationView = binding.navView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)



        
    }
}