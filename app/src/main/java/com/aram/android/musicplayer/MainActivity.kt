package com.aram.android.musicplayer

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */

    val TAG = "MainActivity"
    var REQUEST_WRITE_EXTERNAL_STORAGE = 0

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

        val mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter
    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter : FragmentPagerAdapter {
        var fm: FragmentManager

        constructor(fm: FragmentManager) : super(fm) {
            this.fm = fm
        }

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            when (position) {
                0 -> return MusicOverviewFragment.newInstance()
                1 -> return MusicControlsFragment.newInstance()
                else -> {
                    val mlFragment: Fragment = MusicListFragment.newInstance()
                    /*
                    mlFragment.sharedElementEnterTransition = MusicTransition()
                    mlFragment.enterTransition = Fade()
                    mlFragment.exitTransition = Fade()
                    mlFragment.sharedElementReturnTransition = MusicTransition()
                            fm.beginTransaction()
                            .addSharedElement(albumArtImageView, "albumArtPreview")
                            .replace(R.id.container, mlFragment)
                            .commit()
                            */
                    return mlFragment
                }
            }
        }

        override fun getCount(): Int {
            return 3
        }
    }
}