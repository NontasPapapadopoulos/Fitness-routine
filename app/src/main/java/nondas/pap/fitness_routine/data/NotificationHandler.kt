package nondas.pap.fitness_routine.data

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.media.AudioAttributes
import android.os.Build
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import android.provider.Settings.System.DEFAULT_NOTIFICATION_URI
import android.util.Log
import java.util.Calendar

class NotificationHandler(private val context: Context) {

    companion object {
        const val CHANNEL_ID = "NOTIFICATION_CHANNEL"
    }

    fun createNotificationChannelAndSchedule() {
        createNotificationChannel()
        scheduleDailyNotification()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Daily Notifications",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Daily reminder"
            setShowBadge(true)
            enableLights(true)
            enableVibration(true)
            vibrationPattern = longArrayOf(0, 300, 200, 300)
            setSound(
                DEFAULT_NOTIFICATION_URI,
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
        }
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    fun scheduleDailyNotification() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                // Ask user to grant permission
                val intent = Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
                return
            }
        }

        val intent = Intent(context, DailyNotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 20)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
}