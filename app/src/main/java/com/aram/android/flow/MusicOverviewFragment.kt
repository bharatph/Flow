package com.aram.android.flow

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import shortbread.Shortcut

/**
 * A placeholder fragment containing a simple view.
 */
@Shortcut(id = "movies", icon = R.drawable.info, shortLabel = "Play")

class MusicOverviewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_music_overview, container, false)
        //rootView.cool.text = getString(R.string.section_format, arguments.getInt(ARG_SECTION_NUMBER))

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
        fun newInstance(): MusicOverviewFragment {
            val fragment = MusicOverviewFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}