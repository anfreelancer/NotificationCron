package com.luteapp.notificationcron.data.model.backup

import com.luteapp.notificationcron.data.model.backup.v00500.NotificationCron
import com.luteapp.notificationcron.data.model.backup.v00500.Settings
import com.squareup.moshi.JsonClass

const val V00500 = "500"

@JsonClass(generateAdapter = true)
data class BackupV00500(
    val notificationCrons: List<NotificationCron>,
    val settings: Settings
) : Backup(V00500)