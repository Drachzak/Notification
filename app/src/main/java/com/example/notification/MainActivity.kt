package com.example.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.core.app.NotificationCompat
import com.example.notification.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var notificationmanager: NotificationManager? = null

    private val channel_id = "channel_1"

    private lateinit var binding: ActivityMainBinding

    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notificationmanager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(channel_id,"Countdown", "Notif When Countdown End")

        binding.btnStart.setOnClickListener {
            countDownTimer.start()
        }

        countDownTimer = object : CountDownTimer(10000,1000){
            override fun onTick(p0: Long) {
                binding.timer.text = getString(R.string.time_reamining, p0/1000)
            }

            override fun onFinish() {
                displayNotification()
            }

        }

    }

    private fun displayNotification() {
        val notificationid = 45
        val notification = NotificationCompat.Builder(this, channel_id)
            .setContentTitle("Countdown Timer")
            .setContentText("your timer end")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        notificationmanager?.notify(notificationid, notification)
    }

    private fun createNotificationChannel(id: String, name: String, channelDescription: String) {
        //validasi notif akan di buat jika sdk itu 26+
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, importance).apply {
                description = channelDescription
            }
            notificationmanager?.createNotificationChannel(channel)
        }
    }
}