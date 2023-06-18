package com.example.eshfeenygraduationproject.eshfeeny.util

import android.Manifest
import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.eshfeenygraduationproject.R


const val notificationId = 10
const val channelId = "Alarm Channel"
const val titleExtra = "medicName"
const val descExtra = "medicDesc"

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Show a notification with the data you want to display:
        val medicName = intent.getStringExtra("medicName")
        val medicDesc = intent.getStringExtra("medicDesc")
        println("alarm test $medicName  $medicDesc")

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.logo_eshfeeny)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText(intent.getStringExtra(descExtra))
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationId, notification)
    }
}