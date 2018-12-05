package com.aram.android.flow

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aram.android.flow.adapter.AlbumCarouselAdapter
import com.aram.android.flow.adapter.SongListAdapter
import com.aram.android.flow.listener.RecyclerViewItemClickListener
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

        //TODO Get Input from Android
        val albumArrayName: ArrayList<String> = arrayListOf(
                "Prism",
                "Roar",
                "The Weekend"
        )

        val albumArrayImage: ArrayList<Drawable> = arrayListOf(
                context!!.getDrawable(R.drawable.art)!!,
                context!!.getDrawable(R.drawable.coverart)!!,
                context!!.getDrawable(R.drawable.star)!!
        )

        rootView.albumList.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
        rootView.albumList.adapter = AlbumCarouselAdapter(context!!, albumArrayImage)
        rootView.albumList.addOnPageChangedListener { _, p1 ->
            run {
                rootView.albumArtTextView.text = albumArrayName[p1]
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