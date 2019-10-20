package com.aram.android.flow

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.aram.android.flow.model.Album
import com.aram.android.flow.model.Song

/**
 * Created by bharatvaj on 17-12-2017.
 */

object MusicController {

    val TAG = "MusicController"
    val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
    val project : Array<String> = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION
    )

    var playingSong: Song? = null

    fun isPlaying(song: Song? = null) : Boolean {
        if(playingSong == null){
            return false
        }
        if(song == null){
            return playingSong != null
        }
        return playingSong?.id == song.id
    }

    private fun getSongsForCursor(musicCursor: Cursor?) :  ArrayList<Song> {
        var songs = ArrayList<Song>()
        if(musicCursor == null ) return songs
        val idCol = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID)
        val albumIdCol = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
        val titleCol = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
        val artistCol = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
        val albumCol = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
        val durationCol = musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
        while (musicCursor.moveToNext()) {
            val thisID = musicCursor.getLong(idCol)
            val thisAlbumId = musicCursor.getLong(albumIdCol)
            val thisTitle = musicCursor.getString(titleCol)
            val thisArtist = musicCursor.getString(artistCol)
            val thisAlbum = musicCursor.getString(albumCol)
            val thisDuration = musicCursor.getLong(durationCol)
            songs.add(Song(thisID, thisAlbumId, thisTitle, thisAlbum, thisArtist, thisDuration))
        }
        musicCursor.close()
        return songs
    }

    fun getAllSongs(context: Context?): ArrayList<Song> {
        val musicCursor = context?.contentResolver?.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                project,
                selection,
                null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER)
        return getSongsForCursor(musicCursor)
    }

    fun getSongsForAlbum(context: Context?, album: Album) : ArrayList<Song> {
        val selection = MediaStore.Audio.Media.ALBUM_ID + " == " + album.id
        val musicCursor = context?.contentResolver?.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                project,
                selection,
                null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER)
        return getSongsForCursor(musicCursor)
    }
}
