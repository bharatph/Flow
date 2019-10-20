package com.aram.android.flow.service

import android.app.Service
import android.content.ContentUris
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import com.aram.android.flow.MusicController
import com.aram.android.flow.listener.OnSongSelectListener
import com.aram.android.flow.model.Song
import java.lang.IllegalStateException

/**
 * Created by Home on 21-01-2018.
 */
public class MusicService : Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    private val musicBinder: IBinder = MusicBinder()
    private var player: MediaPlayer = MediaPlayer()
    private var songs: ArrayList<Song>? = null
    private var songPos: Int = 0
    var onSongSelectListener: OnSongSelectListener? = null

    private fun initMusicPlayer() {
        player.setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
        player.setAudioStreamType(AudioManager.STREAM_MUSIC)
        player.setOnPreparedListener(this)
        player.setOnErrorListener(this)
        player.setOnCompletionListener(this)
    }

    override fun onCreate() {
        super.onCreate()
        songPos = 0
        initMusicPlayer()
    }

    fun setList(songs: ArrayList<Song>) {
        this.songs = songs
    }

    inner class MusicBinder : Binder() {

        fun getService(): MusicService {
            return this@MusicService
        }
    }

    override fun onPrepared(mp: MediaPlayer) {
        mp.start()
    }

    fun playSong(song: Song) {
        if(MusicController.playingSong == song){
            player.prepareAsync()
            return
        }
        if (player.isPlaying){
            MusicController.playingSong = null
            player.stop()
            player.reset()
        }
        var trackUri: Uri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, song.id)
        try {
            player.setDataSource(applicationContext, trackUri)
            onSongSelectListener?.onSongSelect(song)
        } catch (e: Exception) {
            Log.e("MUSIC SERVICE", "Error setting data source", e)
        }
        player.setOnPreparedListener {
            MusicController.playingSong = song
            player.start()
        }
        try {
            player.prepareAsync()
            MusicController.playingSong = song
        } catch (ie : IllegalStateException){
            Log.e("MUSIC SERVICE", "Cannot play the music file")
        }
    }


    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        return false
    }

    override fun onCompletion(mp: MediaPlayer?) {
        //
    }

    override fun onBind(intent: Intent?): IBinder? {
        return musicBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        player.stop()
        player.release()
        return false
    }

}