package com.aram.android.flow.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.aram.android.flow.R.id.*
import com.aram.android.flow.R.layout.track_item
import com.aram.android.flow.listener.RecyclerViewItemClickListener
import com.aram.android.flow.model.Song

/**
 * Created by bharatvaj on 11-12-2017.
 */
class SongListAdapter : RecyclerView.Adapter<SongListAdapter.SongListViewHolder> {

    var songList: ArrayList<Song>
    var context: Context
    val clickListener: RecyclerViewItemClickListener

    constructor(context: Context, songList: ArrayList<Song>, clickListner: RecyclerViewItemClickListener) {
        this.context = context
        this.songList = songList
        this.clickListener = clickListner
    }

    override fun onBindViewHolder(holder: SongListViewHolder, position: Int) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongListViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(track_item, parent, false)
        val songlistViewHolder = SongListViewHolder(itemView)
        return songlistViewHolder
    }

    override fun getItemCount(): Int {
        return songList.size
    }

    inner class SongListViewHolder : RecyclerView.ViewHolder {
        var songName: TextView
        var songTime: TextView
        var rootView: View
        constructor(itemView: View) : super(itemView) {
            rootView = itemView
            songName = itemView.findViewById(trackName)
            songTime = itemView.findViewById(trackTime)
        }
    }
}