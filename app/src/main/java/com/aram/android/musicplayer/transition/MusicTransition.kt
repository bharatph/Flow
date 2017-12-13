package com.aram.android.musicplayer.transition

import android.support.transition.ChangeBounds
import android.support.transition.ChangeImageTransform
import android.support.transition.ChangeTransform
import android.support.transition.TransitionSet

/**
 * Created by bharatvaj on 12-12-2017.
 */
class MusicTransition : TransitionSet {
    constructor(){
        ordering = ORDERING_TOGETHER
        addTransition(ChangeBounds())
                .addTransition(ChangeTransform())
                .addTransition(ChangeImageTransform())
    }
}