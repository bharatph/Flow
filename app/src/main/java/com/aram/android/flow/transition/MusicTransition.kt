package com.aram.android.flow.transition

import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.ChangeTransform
import androidx.transition.TransitionSet

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