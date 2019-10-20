package com.aram.android.flow.adapter

import android.content.ContentUris
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.viewpager.widget.ViewPager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.aram.android.flow.R
import com.aram.android.flow.R.id.*
import com.aram.android.flow.R.layout.carousel_item
import com.aram.android.flow.model.Album
import com.aram.android.flow.util.SongUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

/**
 * Created by bharatvaj on 11-12-2017.
 */
class AlbumCarouselAdapter : RecyclerView.Adapter<AlbumCarouselAdapter.AlbumCarouselViewHolder> {

    var albums: ArrayList<Album>
    var reqOptions : RequestOptions

    var context : Context

    constructor(context: Context, albums:ArrayList<Album>){
        this.context = context
        this.albums = albums
        this.reqOptions = RequestOptions().placeholder(R.drawable.coverart).fitCenter().centerCrop()
    }

    override fun onBindViewHolder(holder: AlbumCarouselViewHolder, position: Int) {
        Glide.with(context)
                .load(SongUtil.getAlbumArt(albums[position]))
                .apply(reqOptions)
                .into(holder.albumImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumCarouselViewHolder {
        val itemView : View = LayoutInflater.from(parent?.context).inflate(carousel_item, parent, false)
        return AlbumCarouselViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return albums.size
    }
    class AlbumCarouselViewHolder : RecyclerView.ViewHolder {
        var albumImage : ImageView
        constructor(itemView : View) : super(itemView) {
            albumImage = itemView.findViewById(itemAlbumImageView)

        }
    }
}