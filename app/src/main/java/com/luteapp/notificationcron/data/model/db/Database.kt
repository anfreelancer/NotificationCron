package com.luteapp.notificationcron.data.model.db

data class Database(
    val notificationCrons: List<NotificationCron>,
    val settings: Settings
)