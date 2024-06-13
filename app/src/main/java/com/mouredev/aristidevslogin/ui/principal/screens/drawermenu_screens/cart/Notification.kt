package com.mouredev.aristidevslogin.ui.principal.screens.drawermenu_screens.cart

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.mouredev.aristidevslogin.MainActivity
import com.mouredev.aristidevslogin.R

class Notification(var context: Context, var title: String, var msg: String) {
    val channelId: String = "FCM100"
    val channelName: String = "FCMMessage"
    val notificationManager = context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    lateinit var notificationChannel:NotificationChannel
    lateinit var notificationBuilder:NotificationCompat.Builder

    fun fireNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel=NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_IMMUTABLE)
        notificationBuilder = NotificationCompat.Builder(context,channelId)
        notificationBuilder.setSmallIcon(R.drawable.logo)
        notificationBuilder.addAction(R.drawable.logo,"Abrir",pendingIntent)
        notificationBuilder.setContentTitle(title)
        notificationBuilder.setContentText(msg)
        notificationBuilder.setAutoCancel(true)
        notificationManager.notify(100,notificationBuilder.build())
    }

}