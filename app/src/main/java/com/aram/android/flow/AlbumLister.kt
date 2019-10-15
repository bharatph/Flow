package com.aram.android.flow

import android.content.Context
import android.provider.MediaStore
import com.aram.android.flow.model.Album

object AlbumLister {
    fun getAlbums(context: Context?) : ArrayList<Album>{
        var project = arrayOf (
                MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.NUMBER_OF_SONGS,
                MediaStore.Audio.Albums.ARTIST
        )
        val selection = MediaStore.Audio.Albums.ALBUM+ " != 0"

        val albums = java.util.ArrayList<Album>()

        val albumCursor = context
                ?.contentResolver
                ?.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                        project,
                        selection,
                        null,
                        MediaStore.Audio.Albums.DEFAULT_SORT_ORDER)
        if(albumCursor != null){
            val id = albumCursor.getColumnIndex(MediaStore.Audio.Albums._ID)
            val name = albumCursor.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM)
            val noOfSongs = albumCursor.getColumnIndex(MediaStore.Audio.AlbumColumns.NUMBER_OF_SONGS)
            val artist = albumCursor.getColumnIndex(MediaStore.Audio.AlbumColumns.ARTIST)
            while(albumCursor.moveToNext()){
                val albumId = albumCursor.getLong(id)
                val albumName = albumCursor.getString(name)
                val numberOfSongs = albumCursor.getInt(noOfSongs)
                val artists = albumCursor.getString(artist)
                albums.add(Album(albumId, albumName, artists,numberOfSongs))
            }
        }
        albumCursor?.close()
        return albums
    }
}