package com.tutorialsbuzz.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setClickListener()
    }

    private fun setClickListener() {
        politics.setOnClickListener(this@MainActivity)
        sports.setOnClickListener(this@MainActivity)
        entertainment.setOnClickListener(this@MainActivity)
    }

    override fun onClick(view: View?) {

        when (view?.id) {

            R.id.politics -> {
                //Politics Notification
                createNotification(
                    resources.getString(R.string.channel_politics),
                    resources.getString(R.string.politics_content),
                    resources.getString(R.string.channel_politics),
                    NotificationCompat.PRIORITY_HIGH,
                    100
                )
            }

            R.id.sports -> {
                //Sports Notification
                createNotification(
                    resources.getString(R.string.channel_sports),
                    resources.getString(R.string.sports_content),
                    resources.getString(R.string.channel_sports),
                    NotificationCompat.PRIORITY_DEFAULT,
                    101
                )
            }

            R.id.entertainment -> {
                //Entertainment Notification
                createNotification(
                    resources.getString(R.string.channel_entertainment),
                    resources.getString(R.string.entertainment_content),
                    resources.getString(R.string.channel_entertainment),
                    NotificationCompat.PRIORITY_LOW,
                    102
                )
            }
        }
    }

    /**
     * Create Notification
     * Param
     * 1. title
     * 2. content
     * 3. channelId
     * 4.priorty
     * 5. notificationId
     */
    fun createNotification(
        title: String,
        content: String,
        channelId: String,
        priorty: Int,
        notificationID: Int
    ) {

        // Create an explicit intent for an Activity in your app
        val intent = Intent(applicationContext, NotifyActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val extras = Bundle()
        extras.putString(NotifyActivity.notify_title, title)
        extras.putString(NotifyActivity.notify_content, content)
        intent.putExtras(extras)
        intent.action = Intent.ACTION_VIEW

        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(
                applicationContext,
                notificationID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_round))
            .setSmallIcon(R.drawable.ic_notifications_active)
            .setContentTitle(title)
            .setContentText(content)
            .setContentIntent(pendingIntent)
            .setPriority(priorty)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(500, 500, 500))


        with(NotificationManagerCompat.from(applicationContext)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId,
                    channelId,
                    NotificationManager.IMPORTANCE_HIGH
                )
                channel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
                createNotificationChannel(channel)
            }
            // notificationId is a unique int for each notification that you must define
            notify(notificationID, builder.build())
        }

        playNotificationSound(this@MainActivity)
    }

    /**
     * Play sound for notification
     */
    fun playNotificationSound(context: Context) {
        try {
            val defaultSoundUri =
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val r = RingtoneManager.getRingtone(context, defaultSoundUri)
            r.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}