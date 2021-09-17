package com.luteapp.notificationcron.data.receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.luteapp.notificationcron.data.scheduleAlarms

class BootCompletedReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            Thread {
                scheduleAlarms(context)
            }.start()
        }
    }
}