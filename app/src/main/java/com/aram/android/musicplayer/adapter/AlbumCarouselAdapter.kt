package com.aram.android.musicplayer.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.aram.android.musicplayer.R.id.*
import com.aram.android.musicplayer.R.layout.carousel_item

/**
 * Created by bharatvaj on 11-12-2017.
 */
class AlbumCarouselAdapter : RecyclerView.Adapter<AlbumCarouselAdapter.AlbumCarouselViewHolder> {

    lateinit var albumNameList: ArrayList<String>
    lateinit var albumImageList: ArrayList<Drawable>

    constructor(Context: Context, albumNameList: ArrayList<String>, albumImageList:ArrayList<Drawable>){
        this.albumNameList = albumNameList
        this.albumImageList = albumImageList
    }

    override fun onBindViewHolder(holder: AlbumCarouselViewHolder?, position: Int) {
        holder?.albumName?.text = albumNameList[position]
        holder?.albumImage?.setImageDrawable(albumImageList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AlbumCarouselViewHolder {
        val itemView : View = LayoutInflater.from(parent?.context).inflate(carousel_item, parent, false)
        val songlistViewHolder = AlbumCarouselViewHolder(itemView)
        return songlistViewHolder
    }

    override fun getItemCount(): Int {
        return albumNameList.size
    }
    class AlbumCarouselViewHolder : RecyclerView.ViewHolder {
        lateinit var albumName : TextView
        lateinit var albumImage : ImageView
        constructor(itemView : View) : super(itemView) {
            albumName = itemView.findViewById(itemAlbumTextView)
            albumImage = itemView.findViewById(itemAlbumImageView)

        }
    }
}