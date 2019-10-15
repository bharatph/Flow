package com.aram.android.flow.model

import android.net.Uri
import java.time.Duration

data class Song(val id: Long, var title: String, var album: String, var artist: String, var duration: Long?){
    constructor(id: Long) : this(id, "", "", "", null)
}