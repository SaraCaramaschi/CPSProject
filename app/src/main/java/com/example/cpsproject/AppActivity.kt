package com.example.cpsproject

import android.app.Application
import android.preference.PreferenceManager
import java.util.*

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        var change = ""
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val language = sharedPreferences.getString("language", "bak")
        if (language == "Italian") {
            change="it-rIT"
        } else if (language =="English" ) {
            change = "en-us"
        }
        BaseActivity.dLocale = Locale(change) //set any locale you want here
    }
}