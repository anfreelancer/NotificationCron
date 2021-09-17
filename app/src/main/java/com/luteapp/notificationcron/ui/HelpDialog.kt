package com.luteapp.notificationcron.ui

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.luteapp.notificationcron.R

fun showHelpDialog(windowContext: Context) {
    MaterialDialog(windowContext).show {
        title(R.string.help)
        customView(R.layout.dialog_help)
    }
}