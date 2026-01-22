package com.yemenmarket.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.yemenmarket.R
import com.yemenmarket.presentation.feature.main.MainActivity

class FCMService : FirebaseMessagingService() {
    
    companion object {
        private const val CHANNEL_ID = "yemen_market_channel"
        private const val CHANNEL_NAME = "Yemen Market Notifications"
    }
    
    override fun onNewToken(token: String) {
        // Save token to Firestore or your backend
        saveTokenToFirestore(token)
    }
    
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.notification?.let { notification ->
            showNotification(
                title = notification.title ?: "Yemen Market",
                body = notification.body ?: "",
                data = remoteMessage.data
            )
        }
    }
    
    private fun showNotification(title: String, body: String, data: Map<String, String>) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        // Create notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Yemen Market notifications"
            }
            notificationManager.createNotificationChannel(channel)
        }
        
        // Create intent to open the app
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            // Add data to intent if needed
            data.forEach { (key, value) ->
                putExtra(key, value)
            }
        }
        
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        // Build notification
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        
        // Show notification
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
    
    private fun saveTokenToFirestore(token: String) {
        // Implement token saving logic here
        // You'll need FirebaseAuth to get current user ID
    }
}
