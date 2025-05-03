package com.example.reminder.presentation

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.compose.ui.util.trace
import androidx.core.app.NotificationCompat
import com.example.notesapp.R

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message= intent?.getStringExtra(context?.getString(R.string.channel_name))?: return
        //push notifications here
        val notificationManager= context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(context, context.getString(R.string.channel_id))
            .setSmallIcon(R.mipmap.ic_launcher_round) // Replace with your app's icon
            .setContentTitle("Reminder for your Note!")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

}