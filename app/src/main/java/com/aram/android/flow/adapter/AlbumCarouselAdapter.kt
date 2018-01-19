package com.aram.android.flow.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.aram.android.flow.R
import com.aram.android.flow.R.id.*
import com.aram.android.flow.R.layout.carousel_item
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

/**
 * Created by bharatvaj on 11-12-2017.
 */
class AlbumCarouselAdapter : RecyclerView.Adapter<AlbumCarouselAdapter.AlbumCarouselViewHolder> {
    var albumImageList: ArrayList<Drawable>
    var reqOptions : RequestOptions

    var context : Context

    constructor(context: Context, albumImageList:ArrayList<Drawable>){
        this.context = context
        this.albumImageList = albumImageList
        this.reqOptions = RequestOptions().placeholder(R.drawable.coverart).fitCenter().centerCrop()
    }

    override fun onBindViewHolder(holder: AlbumCarouselViewHolder?, position: Int) {
        Glide.with(context)
                .load(albumImageList[position])
                .apply(reqOptions)
                .into(holder?.albumImage!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AlbumCarouselViewHolder {
        val itemView : View = LayoutInflater.from(parent?.context).inflate(carousel_item, parent, false)
        return AlbumCarouselViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return albumImageList.size
    }
    class AlbumCarouselViewHolder : RecyclerView.ViewHolder {
        var albumImage : ImageView
        constructor(itemView : View) : super(itemView) {
            albumImage = itemView.findViewById(itemAlbumImageView)

        }
    }
}