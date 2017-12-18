package com.aram.android.musicplayer

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aram.android.musicplayer.adapter.AlbumCarouselAdapter
import com.aram.android.musicplayer.adapter.SongListAdapter
import kotlinx.android.synthetic.main.content_scrolling.view.*
import kotlinx.android.synthetic.main.fragment_music_list.view.*
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
class MusicListFragment : Fragment() {

    private lateinit var mc : MusicController


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_music_list, container, false)
        mc = MusicController(context!!)
        var songList = mc.getSongList()
        val albumArrayName: ArrayList<String> = arrayListOf(
                "Prism",
                "Roar",
                "The Weekend"
        )
        val albumArrayImage : ArrayList<Drawable> = arrayListOf(
                context!!.getDrawable(R.drawable.art),
                context!!.getDrawable(R.drawable.coverart),
                context!!.getDrawable(R.drawable.star)
        )

        rootView.albumList.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
        rootView.albumList.adapter = AlbumCarouselAdapter(context!!, albumArrayName, albumArrayImage)
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rootView.albumList)

        /*
        var songArrayList : ArrayList<String> = arrayListOf(
                "Roar",
                "Piece of Me",
                "Chained to the rhythm",
                "This is how we do",
                "Legendary Lovers",
                getString(R.string.lorem_ipsum_small)
        )
        */
        rootView.songList.layoutManager = LinearLayoutManager(context!!)
        rootView.songList.adapter = SongListAdapter(context!!, songList)
        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        fun newInstance(): MusicListFragment {
            val fragment = MusicListFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}