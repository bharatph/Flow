package com.aram.android.flow

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import android.os.Bundle
import android.os.IBinder
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import android.util.Log
import com.aram.android.flow.listener.OnDownPressedListener
import com.aram.android.flow.service.MusicService

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_music_controls.*
import kotlinx.android.synthetic.main.song_info.*
import shortbread.Shortcut

@Shortcut(id = "movies", icon = R.drawable.info, shortLabel = "Play")

class MainActivity : AppCompatActivity() {


    private lateinit var mc: MusicController
    private var musicService: MusicService? = null
    private var playIntent: Intent? = null
    private var musicBound = false


    private val TAG = "MainActivity"
    private val REQUEST_WRITE_EXTERNAL_STORAGE = 0

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            REQUEST_WRITE_EXTERNAL_STORAGE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "WRITE_EXTERNAL_STORAGE Granted")
                } else {
                    Log.i(TAG, "WRITE_EXTERNAL_STORAGE Denied")
                }
                return
            }
        }
    }

    fun resolvePermissions() {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        Array<String>(1) { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                        REQUEST_WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resolvePermissions()
        MusicControlsFragment.newInstance()
        MusicListFragment.newInstance()

        val mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, this)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter
    }


    override fun onDestroy() {
        stopService(playIntent)
        super.onDestroy()
    }


    private var musicConnection: ServiceConnection = object : ServiceConnection {

        override fun onServiceDisconnected(name: ComponentName?) {
            musicBound = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            var binder: MusicService.MusicBinder = service as MusicService.MusicBinder
            musicService = binder.getService()
//            musicService!!.setList(MusicController.getAllSongs(service.getServ))
            musicBound = true
        }

    }

    override fun onStart() {
        super.onStart()
//        mc = MusicController(this)
        if (playIntent == null) {
            playIntent = Intent(this, MusicService::class.java)
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE)
            startService(playIntent)
        }
    }

    inner class SectionsPagerAdapter(val fm: FragmentManager, var mainActivity: MainActivity) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return when (position) {
                0 -> MusicControlsFragment.newInstance().apply {
                    onDownPressedListener = object : OnDownPressedListener {
                        override fun onDownPressed() {
                            mainActivity.container.setCurrentItem(1)
                        }

                    }
                }
                1 -> MusicListFragment.newInstance()
                else -> MusicOverviewFragment.newInstance()
            }
        }

        override fun getCount(): Int {
            return 2
        }
    }
}