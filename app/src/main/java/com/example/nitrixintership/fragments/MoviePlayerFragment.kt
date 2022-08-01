package com.example.nitrixintership.fragments


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.nitrixintership.R
import com.example.nitrixintership.databinding.FragmentMoviePlayerBinding
import com.example.nitrixintership.utills.Constants.Companion.KEY_PLAYER_POSITION
import com.example.nitrixintership.utills.Constants.Companion.KEY_PLAY_WHEN_READY
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import kotlinx.android.synthetic.main.custom_playback_view.*
import kotlinx.android.synthetic.main.fragment_movie_player.*
import kotlinx.android.synthetic.main.fragment_movie_player.view.*


class MoviePlayerFragment : Fragment(R.layout.fragment_movie_player), Player.Listener {

    private var _binding: FragmentMoviePlayerBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<MoviePlayerFragmentArgs>()
    private lateinit var exoPlayer: ExoPlayer
    private lateinit var playerView: StyledPlayerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        View = FragmentMoviePlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val videoPlayer = getView()?.findViewById(R.id.video_player) as StyledPlayerView
        setupPlayer(videoPlayer)
        mp4Play()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            exoPlayer.seekTo(it.getLong(KEY_PLAYER_POSITION))
            exoPlayer.playWhenReady = it.getBoolean(KEY_PLAY_WHEN_READY)
        }
        exoPlayer.play()
    }

    private fun mp4Play() {
        val mediaFile = MediaItem.fromUri(args.data.sourcevideo)
        exoPlayer.addMediaItem(mediaFile)
        exoPlayer.prepare()
    }

    override fun onStop() {
        super.onStop()
        exoPlayer.release()
    }

    private fun setupPlayer(videoPlayer:StyledPlayerView) {
        exoPlayer = ExoPlayer.Builder(requireContext()).build()
        playerView = videoPlayer
        playerView.player = exoPlayer
        exoPlayer.addListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_PLAYER_POSITION, exoPlayer.currentPosition)
        outState.putInt(KEY_PLAY_WHEN_READY, exoPlayer.currentMediaItemIndex)
    }

}