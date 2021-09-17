package com.luteapp.notificationcron.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.luteapp.notificationcron.BillingClientSetup
import com.luteapp.notificationcron.R
import com.luteapp.notificationcron.ShopActivity
import com.luteapp.notificationcron.data.model.db.NotificationCron
import com.luteapp.notificationcron.databinding.ActivityMainBinding
import com.luteapp.notificationcron.ui.licenses.LicensesActivity
import com.luteapp.notificationcron.ui.settings.SettingsActivity
import java.util.*

class MainActivity : AppCompatActivity(), NotificationCronAdapter.ViewListener {

    private lateinit var notificationCronViewModel: NotificationCronViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var notificationCronAdapter: NotificationCronAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        loadTheme(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        layoutManager = LinearLayoutManager(this)
        notificationCronAdapter = NotificationCronAdapter(Collections.emptyList(), this)

        recyclerView = findViewById(R.id.cronRecyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = notificationCronAdapter
        val itemTouchHelper = ItemTouchHelper(NotificationCronDragCallback(notificationCronAdapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )

        notificationCronViewModel =
            ViewModelProvider(this).get(NotificationCronViewModel::class.java)
        notificationCronViewModel.allNotificationCrons.observe(
            this,
            { notificationCrons ->
                notificationCronAdapter.setData(notificationCrons)
            })

        binding.addButton.setOnClickListener {
            if (BillingClientSetup.isUpgraded(applicationContext)) {
                showCreateDialog(this) {
                    notificationCronViewModel.create(this, it)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.settings -> {
                if (BillingClientSetup.isUpgraded(applicationContext)) {
                    startActivity(Intent(this, SettingsActivity::class.java))

                }
                return true
            }
            R.id.premium -> {
                startActivity(Intent(this, ShopActivity::class.java))
                return true
            }
            R.id.repairSchedule -> {
                if (BillingClientSetup.isUpgraded(applicationContext)) {
                    notificationCronViewModel.repairSchedule(this)

                }
                return true
            }
            R.id.licenses -> {
                if (BillingClientSetup.isUpgraded(applicationContext)) {
                    startActivity(Intent(this, LicensesActivity::class.java))
                }
                return true
            }
            R.id.imprint -> {
                if (BillingClientSetup.isUpgraded(applicationContext)) {
                    showImprintDialog(this)
                }
                return true
            }
            R.id.help -> {
                if (BillingClientSetup.isUpgraded(applicationContext)) {
                    showHelpDialog(this)
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun testNotificationCron(notificationCron: NotificationCron) {
        showNotification(this, notificationCron)
    }

    override fun enableNotificationCron(notificationCron: NotificationCron) {
        notificationCron.enabled = true
        notificationCronViewModel.update(this, notificationCron)
    }

    override fun disableNotificationCron(notificationCron: NotificationCron) {
        notificationCron.enabled = false
        notificationCronViewModel.update(this, notificationCron)
    }

    override fun editNotificationCron(notificationCron: NotificationCron) {
        showUpdateDialog(this, notificationCron) {
            notificationCronViewModel.update(this, it)
        }
    }

    override fun deleteNotificationCron(notificationCron: NotificationCron) {
        showDeleteDialog(this) {
            notificationCronViewModel.delete(this, notificationCron)
        }
    }

    override fun moveNotificationCrons(notificationCrons: List<NotificationCron>) {
        notificationCronViewModel.updateAfterMove(notificationCrons)
    }
}
