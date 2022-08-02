package com.example.nitrixintership.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.nitrixintership.R
import com.example.nitrixintership.utills.Constants.Companion.KEY_PLAYER_POSITION
import com.example.nitrixintership.utills.Constants.Companion.KEY_PLAY_WHEN_READY
import com.example.nitrixintership.utills.observeOnce
import com.example.nitrixintership.viewmodel.PlayerViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView


class MoviePlayerFragment : Fragment(R.layout.fragment_movie_player), Player.Listener {

    private val args by navArgs<MoviePlayerFragmentArgs>()
    private lateinit var exoPlayer: ExoPlayer
    private lateinit var playerView: StyledPlayerView
    private lateinit var playerViewModel: PlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playerViewModel = ViewModelProvider(requireActivity())[PlayerViewModel::class.java]
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val videoPlayer = getView()?.findViewById(R.id.video_player) as StyledPlayerView
        setupPlayer(videoPlayer)
        mp4Play()
        requestApiData()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            exoPlayer.seekTo(it.getLong(KEY_PLAYER_POSITION))
            exoPlayer.playWhenReady = it.getBoolean(KEY_PLAY_WHEN_READY)
        }
        exoPlayer.play()
    }

    override fun onStop() {
        super.onStop()
        exoPlayer.release()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_PLAYER_POSITION, exoPlayer.currentPosition)
        outState.putInt(KEY_PLAY_WHEN_READY, exoPlayer.currentMediaItemIndex)
    }

    private fun mp4Play() {
        val firstMedia=MediaItem.fromUri(args.data.sourcevideo)
        exoPlayer.addMediaItem(firstMedia)
        exoPlayer.prepare()
        exoPlayer.play()
    }

    private fun setupPlayer(videoPlayer: StyledPlayerView) {
        exoPlayer = ExoPlayer.Builder(requireContext()).build()
        playerView = videoPlayer
        playerView.player = exoPlayer
        exoPlayer.addListener(this)
    }

    private fun requestApiData() {
        playerViewModel.getVideos()
        playerViewModel.listVideos.observeOnce(viewLifecycleOwner) {
           for (i in 0..10) {
                if(args.data.id == it.result[i].id) continue
               exoPlayer.addMediaItem(
                    it.result[i].id,
                    MediaItem.fromUri(it.result[i].video)
                )
            }
        }
    }

}
