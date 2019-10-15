package com.aram.android.flow

import android.content.Context
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.MotionEventCompat
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration

/**
 * Created by bharatvaj on 17-12-2017.
 */

class ReturnCoordinatorLayout : CoordinatorLayout {
    constructor(context: Context):super(context){
    }
    constructor(context: Context, attrs : AttributeSet):super(context, attrs){
    }

    private var mTouchSlop : Int = 0
    var vc : ViewConfiguration = ViewConfiguration.get(context)

    var mIsScrolling : Boolean = false

    override fun onInterceptTouchEvent(ev : MotionEvent) : Boolean{

        val action : Int = MotionEventCompat.getActionMasked(ev)

        when(action){
            MotionEvent.ACTION_UP ->
                    Log.i("MANIPULATION", "UP")
            MotionEvent.ACTION_DOWN ->
                    Log.i("MANIPULATION", "DOWN")
            MotionEvent.ACTION_MOVE ->
                    Log.i("MANIPULATION", "MOVE")
            MotionEvent.ACTION_CANCEL ->
                    Log.i("MANIPULATION", "CHILD CANCELLED")
            MotionEvent.ACTION_SCROLL ->
                    Log.i("MANIPULATION", "SCROLL")
        }

        return false
    }

    override fun onNestedFling(target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        Log.i("FLING", "FLinged")
        return super.onNestedFling(target, velocityX, velocityY, consumed)
    }

}