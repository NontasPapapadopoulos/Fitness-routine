package nondas.pap.fitness_routine.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import nondas.pap.fitness_routine.R
import nondas.pap.fitness_routine.data.NotificationHandler.Companion.CHANNEL_ID
import nondas.pap.fitness_routine.presentation.MainActivity

class DailyNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        showNotification(context)

        // RESCHEDULE for tomorrow (this is the key!)
        NotificationHandler(context).scheduleDailyNotification()
    }

    private fun showNotification(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = createNotificationChannel()
        notificationManager.createNotificationChannel(channel)
        val notification = createNotification(context)
        notificationManager.notify(1, notification)
    }

    private fun createNotificationChannel() = NotificationChannel(
        CHANNEL_ID,
        "Daily Notifications",
        NotificationManager.IMPORTANCE_HIGH
    ).apply {
        description = "Daily reminder notifications"
    }

    private fun createNotification(context: Context) = NotificationCompat.Builder(context, CHANNEL_ID)
        .setContentTitle("Reminder")
        .setContentText("Don't forget to track your training!")
        .setSmallIcon(R.drawable.baseline_fitness_center_24)
        .setAutoCancel(true)
        .setContentIntent(getContentPendingIntent(context))
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .build()

    private fun getContentPendingIntent(context: Context) = PendingIntent.getActivity(
        context,
        0,
        getOpenIntent(context),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    private fun getOpenIntent(context: Context): Intent {
        val openIntent = Intent(context, MainActivity::class.java)
        openIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        return openIntent
    }
}

