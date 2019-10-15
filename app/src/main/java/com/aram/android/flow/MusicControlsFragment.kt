package com.aram.android.flow

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.aram.android.flow.listener.EventListener
import com.aram.android.flow.model.Song
import com.aram.android.flow.service.MusicService
import kotlinx.android.synthetic.main.fragment_music_controls.*
import kotlinx.android.synthetic.main.music_view.*
import kotlinx.android.synthetic.main.music_view.view.*
import kotlinx.android.synthetic.main.song_info.view.*
import rm.com.audiowave.AudioWaveView
import rm.com.audiowave.OnProgressListener
import rm.com.audiowave.OnSamplingListener
import shortbread.Shortcut
import java.util.*

/**
 * A placeholder fragment containing a simple view.
 */

class MusicControlsFragment : Fragment() {

    private var musicService: MusicService? = null
    private var playIntent: Intent? = null
    private var musicBound = false

    private lateinit var artistName : TextView
    private lateinit var songName : TextView
    private lateinit var audioWaveView: AudioWaveView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_music_controls, container, false)
        //rootView.cool.text = getString(R.string.section_format, arguments.getInt(ARG_SECTION_NUMBER))
        artistName = rootView.textArtistName
        songName = rootView.textSongName
        audioWaveView = rootView.audioWaveView
        audioWaveView.onProgressListener = object: OnProgressListener {
            override fun onStartTracking(progress: Float) {
            }
            override fun onStopTracking(progress: Float) {
            }
            override fun onProgressChanged(progress: Float, byUser: Boolean) {
            }
        }
        return rootView
    }



    private var musicConnection: ServiceConnection = object : ServiceConnection {

        override fun onServiceDisconnected(name: ComponentName?) {
            musicBound = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            var binder: MusicService.MusicBinder = service as MusicService.MusicBinder
            musicService = binder.getService()
//            musicService!!.setList(MusicController.getAllSongs())
            musicService!!.setOnPlayListener(object : EventListener {
                override fun onEvent(obj: Any?) {
                    var song : Song = obj as Song
                    artistName.text = song.artist
                    songName.text = song.title
                    //TODO get dynamic data from song object
                    var barr : ByteArray = ByteArray(101)
                    for(i in 0..100){
                        barr[i] = Random().nextInt().toByte()
                    }
                    audioWaveView.setRawData(barr)
                    //TODO END
                }
            })
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