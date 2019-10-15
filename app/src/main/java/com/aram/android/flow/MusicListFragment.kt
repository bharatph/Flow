package com.aram.android.flow

import android.content.*
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aram.android.flow.adapter.AlbumCarouselAdapter
import com.aram.android.flow.adapter.SongListAdapter
import com.aram.android.flow.listener.RecyclerViewItemClickListener
import com.aram.android.flow.model.Album
import com.aram.android.flow.service.MusicService
import kotlinx.android.synthetic.main.content_scrolling.view.*
import kotlinx.android.synthetic.main.fragment_music_list.view.*
import java.util.*

/**
 * A placeholder fragment containing a simple view.
 */
class MusicListFragment : Fragment() {

    private lateinit var mc: MusicController
    private var musicService: MusicService? = null
    private var playIntent: Intent? = null
    private var musicBound = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_music_list, container, false)

        var project = arrayOf (
                MediaStore.Audio.AlbumColumns.ALBUM_ID,
                MediaStore.Audio.AlbumColumns.ALBUM,
//                MediaStore.Audio.AlbumColumns.NUMBER_OF_SONGS,
                MediaStore.Audio.AlbumColumns.ARTIST
        )
        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"

        val albums = ArrayList<Album>()

        val albumCursor = context!!
                .contentResolver
                .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        project,
                        selection,
                        null,
                        MediaStore.Audio.Albums.DEFAULT_SORT_ORDER)
        if(albumCursor != null){
            val id = albumCursor.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM_ID)
            val name = albumCursor.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM)
//            val noOfSongs = albumCursor.getColumnIndex(MediaStore.Audio.AlbumColumns.NUMBER_OF_SONGS)
            val artist = albumCursor.getColumnIndex(MediaStore.Audio.AlbumColumns.ARTIST)
            while(albumCursor.moveToNext()){
                val albumId = albumCursor.getLong(id)
                val albumName = albumCursor.getString(name)
                val numberOfSongs = 0
                val artists = albumCursor.getString(artist)
                albums.add(Album(albumId, albumName, arrayListOf(artists),numberOfSongs))
            }
        }
        albumCursor?.close()

        rootView.albumList.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
        rootView.albumList.adapter = AlbumCarouselAdapter(context!!, albums)
        rootView.albumList.addOnPageChangedListener { _, p1 ->
            run {
                rootView.albumArtTextView.text = albums[p1].name
                var songList = mc.getSongList()
                musicService?.setList(songList)
                rootView.songList.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
                rootView.songList.adapter = SongListAdapter(context!!, songList, RecyclerViewItemClickListener { _, position ->
                    musicService?.setSong(position)
                    musicService?.playSong()
                })
            }
        }
        //TODO END

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rootView.albumList)

        return rootView
    }


    private var musicConnection: ServiceConnection = object : ServiceConnection {

        override fun onServiceDisconnected(name: ComponentName?) {
            musicBound = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            var binder: MusicService.MusicBinder = service as MusicService.MusicBinder
            musicService = binder.getService()
            musicService!!.setList(mc.getSongList())//TODO Tech dept
            musicBound = true
        }

    }

    override fun onStart() {
        super.onStart()
        mc = MusicController(context!!)
        if (playIntent == null) {
            playIntent = Intent(context, MusicService::class.java)
            context!!.bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE)
        }
    }

    companion object {
        fun newInstance(): MusicListFragment {

            val fragment = MusicListFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}