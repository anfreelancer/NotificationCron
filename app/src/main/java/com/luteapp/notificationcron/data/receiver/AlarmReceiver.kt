package com.luteapp.notificationcron.data.receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.luteapp.notificationcron.data.local.AppDatabase
import com.luteapp.notificationcron.data.scheduleNextAlarm
import com.luteapp.notificationcron.ui.showNotification

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null) {
            val notificationCronId = intent.getLongExtra(NOTIFICATION_CRON_ID_EXTRA, Long.MIN_VALUE)
            if (notificationCronId > Long.MIN_VALUE) {
                val database = AppDatabase.getDatabase(context)
                val notificationCronDao = database.notificationCronDao()
                Thread(Runnable {
                    val notificationCron = notificationCronDao.findById(notificationCronId)
                    notificationCron?.let {
                        showNotification(context, notificationCron)
                        scheduleNextAlarm(context, notificationCronDao, notificationCron)
                    }
                }).start()
            }
        }
    }

    companion object {

        private const val ALARM_INTENT_ACTION = "com.luteapp.notificationcron.ALARM"
        private const val NOTIFICATION_CRON_ID_EXTRA = "NOTIFICATION_CRON_ID_EXTRA"

        fun getPendingIntent(context: Context, notificationCronId: Long): PendingIntent {
            val intent = Intent(context, AlarmReceiver::class.java)
            intent.action = ALARM_INTENT_ACTION
            // a unique type is needed so that the alarm manager regards two intent as different (Intent.filterEquals)
            intent.type = "$notificationCronId"
            intent.putExtra(NOTIFICATION_CRON_ID_EXTRA, notificationCronId)
            return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        }
    }
}