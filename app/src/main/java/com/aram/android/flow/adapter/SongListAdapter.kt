package com.aram.android.flow.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.aram.android.flow.MusicController
import com.aram.android.flow.R
import com.aram.android.flow.R.id.*
import com.aram.android.flow.R.layout.track_item
import com.aram.android.flow.listener.RecyclerViewItemClickListener
import com.aram.android.flow.model.Song
import com.aram.android.flow.util.Time

/**
 * Created by bharatvaj on 11-12-2017.
 */
class SongListAdapter(private var context: Context, private var songList: ArrayList<Song>, private val clickListener: RecyclerViewItemClickListener) : RecyclerView.Adapter<SongListAdapter.SongListViewHolder>() {

    override fun onBindViewHolder(holder: SongListViewHolder, position: Int) {
        val song = songList[position]
        if(MusicController.isPlaying(song)){
            holder.itemView.background =
                    context.getDrawable(R.drawable.item_bg_playing)
        }
        holder.rootView.setOnClickListener {
            clickListener.onClick(holder.rootView, position)
        }
        holder.songName.text = song.title
        holder.songTime.text = Time.millisToString(song.duration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongListViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(track_item, parent, false)
        return SongListViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return songList.size
    }

    inner class SongListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var songName: TextView = itemView.findViewById(trackName)
        var songTime: TextView = itemView.findViewById(trackTime)
        var rootView: View = itemView

    }
}