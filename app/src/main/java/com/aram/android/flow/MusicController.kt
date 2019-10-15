package com.aram.android.flow

import android.content.Context
import android.provider.MediaStore
import android.util.Log
import com.aram.android.flow.model.Song
import com.aram.android.flow.service.MusicService

/**
 * Created by bharatvaj on 17-12-2017.
 */

class MusicController {

    val TAG = "MusicController"
    val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
    val project : Array<String> = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION
    )
    var context: Context

    var songs = ArrayList<Song>()

    constructor(context: Context) {
        this.context = context
    }

    companion object {
        var isPlaying = false
    }

    fun getSongList(): ArrayList<Song> {
        val musicCursor = context.contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                project,
                selection,
                null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER)
        songs = ArrayList()
        if (musicCursor != null) {
            val idCol = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val titleCol = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val artistCol = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val albumCol = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
            while (musicCursor.moveToNext()) {
                val thisID = musicCursor.getLong(idCol)
                val thisTitle = musicCursor.getString(titleCol)
                val thisArtist = musicCursor.getString(artistCol)
                val thisAlbum = musicCursor.getString(albumCol)
                songs.add(Song(thisID, null, thisTitle, thisAlbum, thisArtist, null))
            }
            musicCursor.close()
        }
        return songs
    }
}
