package com.aram.android.flow.util

import android.content.ContentUris
import android.net.Uri
import com.aram.android.flow.model.Album
import com.aram.android.flow.model.Song

object SongUtil {
    fun getAlbumArt(song: Song) : Uri {
        var sArtworkUri = Uri.parse("content://media/external/audio/albumart")
        var uri : Uri = ContentUris.withAppendedId(sArtworkUri, song.albumId)
        return uri
    }

    fun getAlbumArt(album: Album) : Uri {
        var sArtworkUri = Uri.parse("content://media/external/audio/albumart")
        var uri : Uri = ContentUris.withAppendedId(sArtworkUri, album.id)
        return uri
    }
}