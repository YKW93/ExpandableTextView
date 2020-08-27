package com.wayne.expandabletextview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue.COMPLEX_UNIT_PX
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.Px
import kotlinx.android.synthetic.main.expandable_textview_layout.view.*

class ExpandableTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var stateChangeListener: OnStateChangeListener? = null

    private var currentState = State.EXPANDED
        set(value) {
            field = value
            stateChangeListener?.onStateChanged(value)
        }

    val isExpanded
        get() = currentState == State.EXPANDED


    var collapsedLines: Int = DEFAULT_MAX_LINES

    var text: String = ""
        set(value) {
            field = value
            tv_origin_text.text = value
        }

    var textColor: Int = Color.BLACK
        set(value) {
            field = value
            tv_origin_text.setTextColor(value)
        }

    @Px
    var textSize: Float = dpToPx(14)
        set(value) {
            field = value
            tv_origin_text.setTextSize(COMPLEX_UNIT_PX, textSize)
        }

    @Px
    var textLineSpacingExtra = dpToPx(8)
        set(value) {
            field = value
            tv_origin_text.setLineSpacing(value, 1.0f)
        }

    @Px
    var gradientViewHeight: Int = dpToPx(16).toInt()
        set(value) {
            field = value
            view_gradient.layoutParams.height = value
        }

    init {
        val view =
            LayoutInflater.from(context).inflate(R.layout.expandable_textview_layout, this, false)
        addView(view)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView, defStyleAttr, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        tv_origin_text.ellipsize = TextUtils.TruncateAt.END
        collapsedLines =
            typedArray.getInteger(
                R.styleable.ExpandableTextView_expandable_text_collapsedLines,
                DEFAULT_MAX_LINES
            )

        text = typedArray.getString(R.styleable.ExpandableTextView_expandable_text) ?: ""

        textColor =
            typedArray.getColor(R.styleable.ExpandableTextView_expandable_text_color, Color.BLACK)

        textSize = typedArray.getDimensionPixelSize(
            R.styleable.ExpandableTextView_expandable_text_size,
            context.resources.getDimensionPixelSize(R.dimen.default_text_size)
        ).toFloat()

        textLineSpacingExtra = typedArray.getDimensionPixelSize(
            R.styleable.ExpandableTextView_expandable_text_line_spacing_extra,
            context.resources.getDimensionPixelSize(R.dimen.default_line_spacing_extra)
        ).toFloat()

        gradientViewHeight = typedArray.getDimensionPixelSize(
            R.styleable.ExpandableTextView_expandable_gradient_view_height,
            context.resources.getDimensionPixelSize(R.dimen.default_gradient_view_height)
        )
        typedArray.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        initTextViewClickListener()
        collapseTextView()
    }

    private fun initTextViewClickListener() {
        tv_origin_text.setOnClickListener {
            if (isExpanded) {
                collapseTextView()
            } else {
                expandTextView()
            }
        }
    }

    private fun expandTextView() {
        if (currentState == State.EXPANDED) {
            return
        }
        currentState = State.EXPANDED
        tv_origin_text.maxLines = Int.MAX_VALUE
        view_gradient.visibility = View.GONE
    }

    private fun collapseTextView() {
        if (currentState == State.COLLAPSED) {
            return
        }
        currentState = State.COLLAPSED
        tv_origin_text.maxLines = collapsedLines
        view_gradient.visibility = View.VISIBLE
    }

    enum class State {
        EXPANDED,
        COLLAPSED,
    }

    companion object {
        const val DEFAULT_MAX_LINES = 4
    }
}