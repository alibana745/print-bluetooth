package com.ali.print_bluetooth

import android.app.Application
import android.support.v7.app.AppCompatDelegate

class App : Application(){

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: App
            private set
    }

    var printerName:String = ""
}