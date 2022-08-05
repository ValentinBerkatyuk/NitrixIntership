package com.example.nitrixintership

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import androidx.navigation.navArgs
import com.example.nitrixintership.service.AudioService
import com.example.nitrixintership.utills.Constants.Companion.KEY_DATA
import com.example.nitrixintership.utills.Constants.Companion.KEY_PLAYER_POSITION
import com.example.nitrixintership.utills.Constants.Companion.KEY_PLAY_WHEN_READY
import com.google.android.exoplayer2.ui.StyledPlayerView

class PlayerActivity : AppCompatActivity() {

    private val args by navArgs<PlayerActivityArgs>()
    lateinit var playerView: StyledPlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        playerView = findViewById(R.id.video_player)
    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(this, AudioService::class.java)
        intent.putExtra(KEY_DATA, args.data)
        startsService(intent)
    }


    private fun startsService(intent: Intent) {
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
        startService(intent)
    }


    private val connection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            if (service is AudioService.VideoServiceBinder) {
                print("service audio service player set")
                playerView.player = service.getExoPlayerInstance()
            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.let {
            playerView.player?.seekTo(it.getLong(KEY_PLAYER_POSITION))
            playerView.player?.playWhenReady = it.getBoolean(KEY_PLAY_WHEN_READY)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        playerView.player?.currentPosition?.let { outState.putLong(KEY_PLAYER_POSITION, it) }
        playerView.player?.currentMediaItemIndex?.let { outState.putInt(KEY_PLAY_WHEN_READY, it) }
    }

    override fun onDestroy() {
        unbindService(connection)
        super.onDestroy()
    }

}
