package com.aram.android.musicplayer

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

/**
 * Example Usage:
 * <ThreesomeLayout id="@+id/threesomeLayout" android:layout_width="match_parent" android:layout_height="match_parent" android:header="song_info" android:content="music_controls" android:footer="playback_controls">
 * ..
 * ..
 * ..
 * />
</ThreesomeLayout> */

/**
 * layout manager that can handle only three views
 * weight can be specified by the child itself
 */
class ThreesomeLayout : ViewGroup {

    private var mHeader: ViewGroup = LinearLayout(context)
    private var mFooter: ViewGroup = LinearLayout(context)
    private var mContent: ViewGroup = LinearLayout(context)
    /** The amount of space used by children in the left gutter.  */
    private val header_height: Int = 0

    /** The amount of space used by children in the right gutter.  */
    private val content_height: Int = 0
    /** The amount of space used by children in the right gutter.  */
    private val footer_height: Int = 0

    /** These are used for computing child frames based on their gravity.  */
    private val mTmpContainerRect = Rect()
    private val mTmpChildRect = Rect()

    var header : ViewGroup
        get() = mHeader
        set(header) {
            mHeader = header
            addView(header)
        }

    var content : ViewGroup
        get() = mContent
        set(content) {
            mContent = content
        }

    var footer : ViewGroup
        get() = mFooter
        set(footer) {
            mFooter = footer
        }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
                attrs, R.styleable.ThreesomeLayout, defStyle, 0)
        try {
            var li : LayoutInflater = LayoutInflater.from(context)
            val headerLayout = a.getResourceId(R.styleable.ThreesomeLayout_header, defStyle)
            mHeader = li.inflate(headerLayout, this) as ViewGroup

            val contentLayout = a.getResourceId(R.styleable.ThreesomeLayout_content, defStyle)
            mContent = li.inflate(contentLayout, this) as ViewGroup

            val footerLayout = a.getResourceId(R.styleable.ThreesomeLayout_footer, defStyle)
            mFooter = li.inflate(footerLayout, this) as ViewGroup
        }
        finally {
            a.recycle()
        }

        /*
        if (a.hasValue(R.styleable.MusicView_exampleDrawable)) {
            exampleDrawable = a.getDrawable(
                    R.styleable.MusicView_exampleDrawable)
            exampleDrawable!!.callback = this
        }
        */
    }

    constructor(context: Context) : super(context){
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    /**
     * Any layout manager that doesn't scroll will want this.
     */
    override fun shouldDelayChildPressedState(): Boolean {
        return false
    }

    /**
     * Ask all children to measure themselves and compute the measurement of this
     * layout based on the children.
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val count = childCount

        // These keep track of the space we are using on the left and right for
        // views positioned there; we need member variables so we can also use
        // these for layout later.

        // Measurement will ultimately be computing these values.
        var maxHeight = 600
        var maxWidth = 600
        val childState = 0

        // Check against our minimum height and width
        maxHeight = Math.max(maxHeight, suggestedMinimumHeight)
        maxWidth = Math.max(maxWidth, suggestedMinimumWidth)

        // Report our final dimensions.
        setMeasuredDimension(View.resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
                View.resolveSizeAndState(maxHeight, heightMeasureSpec,
                        childState shl View.MEASURED_HEIGHT_STATE_SHIFT))
    }

    /**
     * Position all children within this layout.
     */
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val count = childCount

        // These are the far left and right edges in which we are performing layout.
        val leftPos = paddingLeft
        val rightPos = right - left - paddingRight

        // These are the top and bottom edges in which we are performing layout.
        val parentTop = paddingTop
        val parentBottom = bottom - top - paddingBottom


        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility != View.GONE) {
                mTmpChildRect.top += 100
                mTmpChildRect.left = 50
                mTmpChildRect.right = 300
                mTmpChildRect.bottom += 200
                val lp = child.layoutParams as LayoutParams
                val width = child.measuredWidth
                val height = child.measuredHeight
                // Place the child.
                child.layout(mTmpChildRect.left, mTmpChildRect.top,
                        mTmpChildRect.right, mTmpChildRect.bottom)
            }
        }
    }
}
