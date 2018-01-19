package com.aram.android.flow.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.aram.android.flow.R.id.*
import com.aram.android.flow.R.layout.track_item
import com.aram.android.flow.model.Song

/**
 * Created by bharatvaj on 11-12-2017.
 */
class SongListAdapter : RecyclerView.Adapter<SongListAdapter.SongListViewHolder> {

    var songList: ArrayList<Song>

    var context: Context

    constructor(context: Context, songList: ArrayList<Song>) {
        this.context = context
        this.songList = songList
    }

    override fun onBindViewHolder(holder: SongListViewHolder?, position: Int) {
        holder?.songNumber?.text = songList[position].id.toString()
        holder?.songName?.text = songList[position].title
        holder?.songTime?.text = "01:23"
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SongListViewHolder {
        val itemView: View = LayoutInflater.from(parent?.context).inflate(track_item, parent, false)
        val songlistViewHolder = SongListViewHolder(itemView)
        return songlistViewHolder
    }

    override fun getItemCount(): Int {
        return songList.size
    }

    class SongListViewHolder : RecyclerView.ViewHolder {
        var songNumber: TextView
        var songName: TextView
        var songTime: TextView

        constructor(itemView: View) : super(itemView) {
            songNumber = itemView.findViewById(trackNumber)
            songName = itemView.findViewById(trackName)
            songTime = itemView.findViewById(trackTime)
        }
    }
}