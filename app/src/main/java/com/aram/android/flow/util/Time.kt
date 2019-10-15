package com.aram.android.flow.util

object Time {
    fun millisToString(dur: Long?) : String {
        if(dur == null){
            return "--:--"
        }
        // https://stackoverflow.com/questions/25796237/how-to-display-song-duration
        val hrs = dur / 3600000
        val mns = dur / 60000 % 60000
        val scs = dur % 60000 / 1000
        // TODO If hours is  greater than 0, display hours, minutes and seconds.
        return String.format("%02d:%02d", mns, scs)
    }
}