package com.example.digibuddy

import android.app.Application
import com.github.mikephil.charting.utils.Utils

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
    }
}
