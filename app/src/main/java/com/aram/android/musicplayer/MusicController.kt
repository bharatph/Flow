package com.aram.android.musicplayer

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.aram.android.musicplayer.model.Song

/**
 * Created by bharatvaj on 17-12-2017.
 */

internal class MusicController {

    val TAG = "MusicController"
    val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
    val project : Array<String> = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION
    )
    var context: Context

    constructor(context: Context) {
        this.context = context
    }


    companion object {
        var isPlaying = false
    }

    fun play(control: Boolean) {
        if (control) {
            //play music
            MusicController.isPlaying = true
        } else {
            //pause music
            MusicController.isPlaying = false
        }
    }

    fun getSongList(): ArrayList<Song> {
        val musicCursor = context.contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                project,
                selection,
                null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER)
        val songList: ArrayList<Song> = ArrayList<Song>()
        if (musicCursor != null) {
            val idCol = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val titleCol = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val artistCol = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            while (musicCursor.moveToNext()) {
                val thisID = musicCursor.getLong(idCol)
                val thisTitle = musicCursor.getString(titleCol)
                val thisArtist = musicCursor.getString(artistCol)
                songList.add(Song(thisID, thisTitle, thisArtist))
                Log.i(TAG, thisTitle)
            }
        }
        musicCursor.close()
        return songList
    }
}
