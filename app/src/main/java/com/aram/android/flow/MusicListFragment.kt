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

    private var musicService: MusicService? = null
    private var playIntent: Intent? = null
    private var musicBound = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_music_list, container, false)

        val albums = AlbumLister.getAlbums(context)


        rootView.albumList.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
        rootView.albumList.adapter = AlbumCarouselAdapter(context!!, albums)
        rootView.albumList.addOnPageChangedListener { _, p1 ->
            run {
                rootView.albumArtTextView.text = albums[p1].name
                rootView.albumArtArtist.text = albums[p1].artists
                var songList = MusicController.getSongsForAlbum(context, albums[p1])
                musicService?.setList(songList)
                rootView.songList.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
                rootView.songList.adapter = SongListAdapter(context!!, songList, RecyclerViewItemClickListener { _, position ->
                    musicService?.setSong(position)
                    musicService?.playSong()
                })
            }
        }

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
            musicService?.setList(MusicController.getAllSongs(context))//TODO Tech dept
            musicBound = true
        }

    }

    override fun onStart() {
        super.onStart()
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