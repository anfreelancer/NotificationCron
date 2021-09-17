package com.luteapp.notificationcron.ui

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.luteapp.notificationcron.data.local.SettingsDao

fun reloadTheme(activity: Activity) {
    loadTheme(activity)
    activity.recreate()
}

fun loadTheme(context: Context) {
    val settings = SettingsDao(context).readSettings()
    AppCompatDelegate.setDefaultNightMode(settings.theme)
}