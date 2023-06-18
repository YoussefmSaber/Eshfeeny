package com.example.eshfeenygraduationproject.eshfeeny.util

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.eshfeenygraduationproject.R

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Show a notification with the data you want to display:
        val medicName = intent?.getStringExtra("medicName")
        val medicDesc = intent?.getStringExtra("medicDesc")

        val notification = context?.let {
            NotificationCompat.Builder(it, "default")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(medicName)
                .setContentText(medicDesc)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()
        }
        val notificationManager = context?.let { NotificationManagerCompat.from(it) }
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notification?.let {
            notificationManager?.notify(0, it)
        }
    }
}