package com.mag.musicplayer.services.old

import android.media.MediaPlayer
import android.support.v4.media.session.PlaybackStateCompat
import com.mag.musicplayer.data.model.Track


interface PlayerAdapter {

    fun isMediaPlayer(): Boolean

    fun isPlaying(): Boolean

    fun isReset(): Boolean

    fun getCurrentSong(): Track?

    @PlaybackStateCompat.State
    fun getState(): Int

    fun getPlayerPosition(): Int

    fun getMediaPlayer(): MediaPlayer?
    fun initMediaPlayer()

    fun release()

    fun resumeOrPause()

    fun reset()

    fun instantReset()

    fun skip(isNext: Boolean)

    fun seekTo(position: Int)

    fun setPlaybackInfoListener(playbackInfoListener: PlaybackInfoListener)

    fun registerNotificationActionsReceiver(isRegister: Boolean)


    fun setCurrentSong(song: Track, songs: List<Track>)

    fun onPauseActivity()

    fun onResumeActivity()
}
