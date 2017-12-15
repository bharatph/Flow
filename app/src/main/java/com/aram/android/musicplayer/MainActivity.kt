package com.aram.android.musicplayer

import android.support.v7.app.AppCompatActivity

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.support.transition.Fade
import android.support.v4.app.Fragment
import com.aram.android.musicplayer.transition.MusicTransition

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_music_controls.*

class MainActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
    inner class SectionsPagerAdapter :  FragmentPagerAdapter{
        var fm : FragmentManager
        constructor(fm : FragmentManager) : super(fm){
            this.fm = fm
        }
        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            when(position) {
                0 -> return MusicOverviewFragment.newInstance()
                1 -> return MusicControlsFragment.newInstance()
                else -> {
                    val mlFragment : Fragment = MusicListFragment.newInstance()
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