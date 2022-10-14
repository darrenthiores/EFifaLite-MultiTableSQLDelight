package com.darrenthiores.efifalite.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.getSystemService
import com.darrenthiores.efifalite.MainActivity
import com.darrenthiores.efifalite.R
import com.darrenthiores.efifalite.viewModel.OverviewViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@ExperimentalPagerApi
@ExperimentalMaterialApi
class AlarmReceiver: BroadcastReceiver(), KoinComponent {
    private val viewModel by inject<OverviewViewModel>()
    override fun onReceive(context: Context, intent: Intent?) {
        viewModel.updateLogin(true)
        sendNotification(
            context = context,
            title = "Daily Login",
            message = "Don't forget to claim your daily login reward mate!"
        )
    }

    private fun sendNotification(
        context: Context,
        title: String,
        message:String
    ){
        val pendingIntent = getPendingIntent(context)

        val notificationManager = context.getSystemService<NotificationManager>()

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)

            builder.setChannelId(CHANNEL_ID)

            notificationManager?.createNotificationChannel(channel)

        }

        val notification = builder.build()

        notificationManager?.notify(0, notification)

    }

    private fun getPendingIntent(context: Context): PendingIntent? {

        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP

        return TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }

    companion object{

        private const val CHANNEL_ID = "channel_01"
        private const val CHANNEL_NAME = "daily_channel"

    }
}