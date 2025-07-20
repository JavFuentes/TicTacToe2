package dev.javfuentes.tictactoe2.domain.manager

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import dev.javfuentes.tictactoe2.R

class SoundManager(private val context: Context) {

    private var mediaPlayerClick: MediaPlayer? = null
    private var mediaPlayerWin: MediaPlayer? = null
    private var mediaPlayerDefinitiveWin: MediaPlayer? = null

    private var isSoundEnabled = true

    // Volúmenes específicos para cada sonido
    private val clickVolume = 1.0f         // 100%
    private val winVolume = 0.3f           // 50%
    private val definitiveWinVolume = 0.8f // 100%

    init {
        initializeSounds()
    }

    private fun initializeSounds() {
        try {
            // Initialize click sound
            mediaPlayerClick = MediaPlayer.create(context, R.raw.clic)?.apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                setVolume(clickVolume, clickVolume) // 100% volumen
            }

            // Initialize win sound
            mediaPlayerWin = MediaPlayer.create(context, R.raw.win)?.apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                setVolume(winVolume, winVolume) // 50% volumen
            }

            // Initialize definitive win sound
            mediaPlayerDefinitiveWin = MediaPlayer.create(context, R.raw.definitive_win)?.apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                setVolume(definitiveWinVolume, definitiveWinVolume) // 100% volumen
            }

        } catch (e: Exception) {
            Log.e("SoundManager", "Error initializing sounds: ${e.message}")
        }
    }

    fun playClickSound() {
        if (!isSoundEnabled) return

        try {
            mediaPlayerClick?.let { player ->
                if (player.isPlaying) {
                    player.seekTo(0)
                } else {
                    player.start()
                }
            }
        } catch (e: Exception) {
            Log.e("SoundManager", "Error playing click sound: ${e.message}")
        }
    }

    fun playWinSound() {
        if (!isSoundEnabled) return

        try {
            mediaPlayerWin?.let { player ->
                if (player.isPlaying) {
                    player.seekTo(0)
                } else {
                    player.start()
                }
            }
        } catch (e: Exception) {
            Log.e("SoundManager", "Error playing win sound: ${e.message}")
        }
    }

    fun playDefinitiveWinSound() {
        if (!isSoundEnabled) return

        try {
            mediaPlayerDefinitiveWin?.let { player ->
                if (player.isPlaying) {
                    player.seekTo(0)
                } else {
                    player.start()
                }
            }
        } catch (e: Exception) {
            Log.e("SoundManager", "Error playing definitive win sound: ${e.message}")
        }
    }

    fun setSoundEnabled(enabled: Boolean) {
        isSoundEnabled = enabled

        // Stop all sounds if disabled
        if (!enabled) {
            stopAllSounds()
        }
    }

    fun isSoundEnabled(): Boolean = isSoundEnabled

    private fun stopAllSounds() {
        try {
            mediaPlayerClick?.let { if (it.isPlaying) it.pause() }
            mediaPlayerWin?.let { if (it.isPlaying) it.pause() }
            mediaPlayerDefinitiveWin?.let { if (it.isPlaying) it.pause() }
        } catch (e: Exception) {
            Log.e("SoundManager", "Error stopping sounds: ${e.message}")
        }
    }

    fun release() {
        try {
            mediaPlayerClick?.release()
            mediaPlayerWin?.release()
            mediaPlayerDefinitiveWin?.release()

            mediaPlayerClick = null
            mediaPlayerWin = null
            mediaPlayerDefinitiveWin = null
        } catch (e: Exception) {
            Log.e("SoundManager", "Error releasing MediaPlayers: ${e.message}")
        }
    }
}