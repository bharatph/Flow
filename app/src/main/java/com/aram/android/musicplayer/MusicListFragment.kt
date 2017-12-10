package com.aram.android.musicplayer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aram.android.musicplayer.adapter.SongListAdapter
import kotlinx.android.synthetic.main.fragment_music_list.view.*

/**
 * A placeholder fragment containing a simple view.
 */
class MusicListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_music_list, container, false)
        //rootView.lol_label.text = getString(R.string.section_format, arguments.getInt(ARG_SECTION_NUMBER))
        var songArrayList : ArrayList<String> = arrayListOf(
                "Roar",
                "Piece of Me",
                "Chained to the rhythm",
                "This is how we do",
                "Legendary Lovers",
                getString(R.string.lorem_ipsum_small)
        )
        rootView.songList.layoutManager = LinearLayoutManager(context)
        rootView.songList.adapter = SongListAdapter(context, songArrayList)
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