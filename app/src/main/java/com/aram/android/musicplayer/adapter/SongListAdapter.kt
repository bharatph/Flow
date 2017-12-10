package com.aram.android.musicplayer.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.aram.android.musicplayer.R.id.*
import com.aram.android.musicplayer.R.layout.track_item
import kotlinx.android.synthetic.main.track_item.view.*

/**
 * Created by bharatvaj on 11-12-2017.
 */
class SongListAdapter : RecyclerView.Adapter<SongListAdapter.SongListViewHolder> {

    lateinit var trackList : ArrayList<String>

    constructor( Context: Context, trackList : ArrayList<String>){
        this.trackList = trackList
    }

    override fun onBindViewHolder(holder: SongListViewHolder?, position: Int) {
        holder?.songNumber?.text = (position + 1).toString()
        holder?.songName?.text = trackList[position]
        holder?.songTime?.text = "01:23"
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SongListViewHolder {
        val itemView : View = LayoutInflater.from(parent?.context).inflate(track_item, parent, false)
        val songlistViewHolder = SongListViewHolder(itemView)
        return songlistViewHolder
    }

    override fun getItemCount(): Int {
        return trackList.size
    }
    class SongListViewHolder : RecyclerView.ViewHolder {
        lateinit var songNumber : TextView
        lateinit var songName : TextView
        lateinit var songTime : TextView
        constructor(itemView : View) : super(itemView) {
            songNumber = itemView.findViewById(trackNumber)
            songName = itemView.findViewById(trackName)
            songTime = itemView.findViewById(trackTime)
        }
    }
}