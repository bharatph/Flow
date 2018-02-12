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
import android.view.View
import com.aram.android.flow.listener.EventListener
import com.aram.android.flow.model.Song

/**
 * Created by Home on 21-01-2018.
 */
public class MusicService : Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    private val musicBinder: IBinder = MusicBinder()
    private var player: MediaPlayer = MediaPlayer()
    private var songs: ArrayList<Song>? = null
    private var songPos: Int = 0
    private var playListener: EventListener? = null

    public fun initMusicPlayer() {
        player?.setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
        player?.setAudioStreamType(AudioManager.STREAM_MUSIC)
        player?.setOnPreparedListener(this)
        player?.setOnErrorListener(this)
        player?.setOnCompletionListener(this)
    }

    override fun onCreate() {
        super.onCreate()
        songPos = 0
        initMusicPlayer()
    }

    fun setList(songs: ArrayList<Song>) {
        this.songs = songs
    }

    inner class MusicBinder : Binder {
        constructor()

        fun getService(): MusicService {
            return this@MusicService
        }
    }

    /**
     * Called when the media file is ready for playback.
     *
     * @param mp the MediaPlayer that is ready for playback
     */
    override fun onPrepared(mp: MediaPlayer) {
        mp.start()
    }

    fun setSong(index: Int) {
        songPos = index
    }

    fun setOnPlayListener(listener: EventListener){
        this.playListener = listener
    }

    fun playSong() {
        player.reset()
        var song: Song = songs!!.get(songPos)
        var currSong = song.id
        var trackUri: Uri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, currSong)
        song.trackUri = trackUri //FIXME
        try {
            player.setDataSource(applicationContext, trackUri)
            playListener?.onEvent(song)
        } catch (e: Exception) {
            Log.e("MUSIC SERVICE", "Error setting date source", e)
        }
        player.prepareAsync()
    }


    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        return false
    }

    /**
     * Called when the end of a media source is reached during playback.
     *
     * @param mp the MediaPlayer that reached the end of the file
     */
    override fun onCompletion(mp: MediaPlayer?) {
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