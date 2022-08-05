package com.example.nitrixintership.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import com.example.nitrixintership.di.RetrofitModule
import com.example.nitrixintership.model.ListOfVideos
import com.example.nitrixintership.model.Result
import com.example.nitrixintership.utills.Constants.Companion.CHANNEL_ID
import com.example.nitrixintership.utills.Constants.Companion.CHANNEL_NAME
import com.example.nitrixintership.utills.Constants.Companion.KEY_DATA
import com.example.nitrixintership.utills.Constants.Companion.VIDEO_ID
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import retrofit2.Call
import retrofit2.Response

class AudioService : Service(), PlayerNotificationManager.NotificationListener {

    private lateinit var player: ExoPlayer
    lateinit var context: Context
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var mediaSessionConnector: MediaSessionConnector
    private var playerNotificationManager: PlayerNotificationManager? = null

    override fun onBind(intent: Intent?): IBinder {
        return VideoServiceBinder()
    }

    inner class VideoServiceBinder : Binder() {
        fun getExoPlayerInstance() = player
    }

    override fun onCreate() {
        context = this
        player = ExoPlayer.Builder(context).build()
        super.onCreate()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        getData(intent)
        player.prepare()
        player.play()
        createNotificationChannel()
        initManager()
        initMediaConnector()
        return START_STICKY
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    private fun getData(intent: Intent?) {
        val result = intent?.getParcelableExtra<Result>(KEY_DATA)
        val video = result?.sourcevideo?.let { MediaItem.fromUri(it) }
        if (video != null) {
            player.addMediaItem(video)
        }
        RetrofitModule.provideRetrofitInstance().getVideos()
            .enqueue(object : retrofit2.Callback<ListOfVideos> {
                override fun onResponse(
                    call: Call<ListOfVideos>,
                    response: Response<ListOfVideos>
                ) {
                    val data = response.body()?.result
                    if (data != null) {
                        for (i in 0..10) {
                            if (result?.id == data[i].id) continue
                            player.addMediaItem(
                                data[i].id,
                                MediaItem.fromUri(data[i].video)
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<ListOfVideos>, t: Throwable) {
                    Log.d("DATA ERROR", t.toString())
                }
            })
    }

    private fun initManager() {
        playerNotificationManager = PlayerNotificationManager.Builder(
            this, VIDEO_ID, CHANNEL_ID
        ).build()
        playerNotificationManager?.setPlayer(player)
        mediaSession = MediaSessionCompat(context, "MEDIA SESSION_TAG")
        mediaSession.isActive = true
        playerNotificationManager?.setMediaSessionToken(mediaSession.sessionToken)
    }

    private fun initMediaConnector() {
        mediaSessionConnector = MediaSessionConnector(mediaSession)
        mediaSessionConnector.setQueueNavigator(object : TimelineQueueNavigator(mediaSession) {
            override fun getMediaDescription(
                player: Player,
                windowIndex: Int
            ): MediaDescriptionCompat {
                return MediaDescriptionCompat.Builder().build()
            }
        })
        mediaSessionConnector.setPlayer(player)
    }

    override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
        super.onNotificationCancelled(notificationId, dismissedByUser)
        stopSelf()
    }

    override fun onNotificationPosted(
        notificationId: Int,
        notification: Notification,
        ongoing: Boolean
    ) {
        startForeground(notificationId, notification)
    }


    override fun onDestroy() {
        mediaSession.release()
        mediaSessionConnector.setPlayer(null)
        playerNotificationManager?.setPlayer(null)
        player.release()
        super.onDestroy()
    }

}