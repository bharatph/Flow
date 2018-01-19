package com.aram.android.flow

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.music_view.view.*
import shortbread.Shortcut
import java.util.*

/**
 * A placeholder fragment containing a simple view.
 */
@Shortcut(id = "movies", icon = R.drawable.info, shortLabel = "Play")

class MusicControlsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_music_controls, container, false)
        //rootView.cool.text = getString(R.string.section_format, arguments.getInt(ARG_SECTION_NUMBER))

        var barr : ByteArray = ByteArray(100)
        for(i in 0..99){
            barr[i] = Random().nextInt().toByte()
        }
        rootView.audioWaveView.setRawData(barr)
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
        fun newInstance(): MusicControlsFragment {
            val fragment = MusicControlsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}