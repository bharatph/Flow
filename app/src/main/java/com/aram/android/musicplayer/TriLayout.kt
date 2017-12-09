package com.aram.android.musicplayer

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout


class TriLayout : RelativeLayout {

    public val TRILAYOUT_HEADER_HEIGHT = 0.0f
    public val TRILAYOUT_CONTENT_HEIGHT = 0.0f
    public val TRILAYOUT_FOOTER_HEIGHT = 0.0f

    private var mHeader : View? = null
    private var mContent : View? = null
    private var mFooter : View? = null

    private var mHeaderHeight : Float = 0.0f
    private var mContentHeight : Float = 0.0f
    private var mFooterHeight : Float = 0.0f

    var header: View?
        get() = mHeader
        set(header){
            mHeader = header
        }
    /*
     * Setter and getter for mContent
     */

    var content: View?
        get() = mContent
        set(content){
            mContent = content
        }
    /*
     * Setter and getter for mFooter
     */

    var footer: View?
        get() = mFooter
        set(footer) {
            mFooter = footer
        }

    var headerHeight: Float
    get() = mHeaderHeight
    set(headerHeight){
        mHeaderHeight = headerHeight
    }
    var contentHeight: Float
    get() = mContentHeight
    set(contentHeight){
        mContentHeight = contentHeight
    }

    var footerHeight:Float
    get() = mFooterHeight
    set(footerHeight){
        mFooterHeight = footerHeight
    }

    private val TAG = "TriLayout"

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        val a = context.obtainStyledAttributes(attrs,
                R.styleable.TriLayout, defStyle, 0)
        val li : LayoutInflater = LayoutInflater.from(context)
        val headerLayout = a.getResourceId(R.styleable.TriLayout_header,
                defStyle)
        val contentLayout = a.getResourceId(R.styleable.TriLayout_content,
                defStyle)
        val footerLayout = a.getResourceId(R.styleable.TriLayout_footer,
                defStyle)
        li.inflate(headerLayout, this)
        li.inflate(contentLayout, this)
        li.inflate(footerLayout, this)

        mHeader = getChildAt(0)
        mContent = getChildAt(1)
        mFooter = getChildAt(2)

        mHeaderHeight = a.getFloat(R.styleable.TriLayout_headerHeight, TRILAYOUT_HEADER_HEIGHT)
        mContentHeight = a.getFloat(R.styleable.TriLayout_contentHeight, TRILAYOUT_CONTENT_HEIGHT)
        mFooterHeight = a.getFloat(R.styleable.TriLayout_footerHeight, TRILAYOUT_FOOTER_HEIGHT)
        a.recycle()
    }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }
    constructor(context: Context, attrib: AttributeSet) : super(context, attrib) {
        init(attrib, 0)
    }
    constructor(context: Context, attrib: AttributeSet, defStyleAttr: Int) : super(context, attrib, defStyleAttr) {
        init(attrib, defStyleAttr)
    }

    var x = 0
    var y = 0
    override fun onLayout(b: Boolean, i: Int, i1: Int, i2: Int, i3: Int) {
        val absHeaderHeight = (mHeaderHeight * height).toInt()
        val absContentHeight = (mContentHeight * height).toInt()
        val absFooterHeight = (mFooterHeight * height).toInt()
        val posContentEnd = absHeaderHeight + absContentHeight;
        val posFooterEnd = posContentEnd + absFooterHeight;
        mHeader?.layout(0, 0, width, absHeaderHeight)
        mContent?.layout(0, absHeaderHeight, width, posContentEnd)
        mFooter?.layout(0, posContentEnd, width, posFooterEnd)

        mHeader?.invalidate()
        mContent?.invalidate()
        mFooter?.invalidate()

    }
}
