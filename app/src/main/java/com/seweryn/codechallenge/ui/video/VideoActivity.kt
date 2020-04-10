package com.seweryn.codechallenge.ui.video

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.seweryn.codechallenge.R
import kotlinx.android.synthetic.main.activity_video.*


class VideoActivity : AppCompatActivity() {

    private var player: SimpleExoPlayer? = null

    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onResume() {
        super.onResume()
        handleScreenOrientation(resources.configuration)
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        handleScreenOrientation(newConfig)
    }

    private fun handleScreenOrientation(configuration: Configuration) {
        when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                hideSystemUi()
            }
            else -> {
                showSystemUi()
            }
        }
    }

    private fun initializePlayer() {
        val userAgent = Util.getUserAgent(this, getString(R.string.app_name))
        val mediaSource = ProgressiveMediaSource
            .Factory(DefaultDataSourceFactory(this, userAgent))
            .createMediaSource(Uri.parse(getExtraUrl(intent)))
        player = SimpleExoPlayer.Builder(this).build()
        video_player.player = player
        player?.playWhenReady = playWhenReady
        player?.seekTo(currentWindow, playbackPosition)
        player?.prepare(mediaSource, false, false)
    }

    private fun releasePlayer() {
        if (player != null) {
            playWhenReady = player?.playWhenReady == true
            playbackPosition = player?.currentPosition ?: 0
            currentWindow = player?.currentWindowIndex ?: 0
            player?.release()
            player = null
        }
    }

    private fun hideSystemUi() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    private fun showSystemUi() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.decorView.systemUiVisibility = View.VISIBLE
    }

    companion object {
        private val INTENT_EXTRA_URL = "url"

        fun createIntent(context: Context, url: String): Intent {
            return Intent(context, VideoActivity::class.java).apply {
                putExtra(INTENT_EXTRA_URL, url)
            }
        }

        fun getExtraUrl(intent: Intent): String = intent.getStringExtra(INTENT_EXTRA_URL)
    }
}