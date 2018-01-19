package com.aram.android.flow

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.aram.android.flow.adapter.AlbumCarouselAdapter
import com.aram.android.flow.adapter.SongListAdapter
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager
import kotlinx.android.synthetic.main.content_scrolling.view.*
import kotlinx.android.synthetic.main.fragment_music_list.view.*
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
class MusicListFragment : Fragment() {

    private lateinit var mc: MusicController


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_music_list, container, false)

        val albumArrayName: ArrayList<String> = arrayListOf(
                "Prism",
                "Roar",
                "The Weekend"
        )
        val albumArrayImage: ArrayList<Drawable> = arrayListOf(
                context!!.getDrawable(R.drawable.art),
                context!!.getDrawable(R.drawable.coverart),
                context!!.getDrawable(R.drawable.star)
        )

        rootView.albumList.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
        rootView.albumList.adapter = AlbumCarouselAdapter(context!!, albumArrayImage)
        rootView.albumList.addOnPageChangedListener { p0, p1 ->
            run {
                rootView.albumArtTextView.text = albumArrayName[p1]
                mc = MusicController(context!!)
                var songList = mc.getSongList()
                rootView.songList.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
                rootView.songList.adapter = SongListAdapter(context!!, songList)
            }
        }
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