package com.aram.android.flow.model

import android.net.Uri
import java.time.Duration

class Song(val id: Long, var trackUri: Uri?, var title: String, var album: String, var artist: String, var duration: Long?){
    constructor(id: Long) : this(id, null, "", "", "", null)
}