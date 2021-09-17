package com.luteapp.notificationcron.ui

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.luteapp.notificationcron.R

fun showImprintDialog(windowContext: Context) {
    MaterialDialog(windowContext).show {
        title(R.string.imprint)
        customView(R.layout.dialog_imprint)
    }
}