package com.wayne.expandabletextview

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.Px
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.expandable_textview_layout.view.*

class ExpandableTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var stateChangeListener: OnStateChangeListener? = null

    var currentState = State.COLLAPSED
        private set(value) {
            field = value
            when (value) {
                State.EXPANDED -> {
                    tv_origin_text.maxLines = Int.MAX_VALUE
                    view_gradient.visibility = View.GONE
                }
                State.COLLAPSED -> {
                    tv_origin_text.maxLines = collapsedLines
                    view_gradient.visibility = View.VISIBLE
                }
            }
            stateChangeListener?.onStateChanged(value)
        }

    var isActivateExpansion = false
        private set

    val isExpanded
        get() = currentState == State.EXPANDED

    val isCollapsed
        get() = currentState == State.COLLAPSED

    var collapsedLines: Int = DEFAULT_MAX_LINES

    var text: String = ""
        set(value) {
            field = value
            post { updateText(value) }
        }

    var textColor: Int = Color.BLACK
        set(value) {
            field = value
            tv_origin_text.setTextColor(value)
        }

    var textTypeface: Typeface? = null
        set(value) {
            field = value
            tv_origin_text.typeface = value
        }

    @Px
    var textSize: Float = dpToPx(14)
        set(value) {
            field = value
            tv_origin_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
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

        try {
            textTypeface = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                typedArray.getFont(R.styleable.ExpandableTextView_expandable_text_font)
            } else {
                ResourcesCompat.getFont(
                    context,
                    typedArray.getResourceId(R.styleable.ExpandableTextView_expandable_text_font, 0)
                )
            }
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
        }

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
    }

    private fun initTextViewClickListener() {
        tv_origin_text.setOnClickListener {
            toggle()
        }
    }

    fun toggle() {
        when (currentState) {
            State.COLLAPSED -> expand()
            State.EXPANDED -> collapse()
        }
    }

    private fun updateText(value: String?) {
        tv_origin_text.maxLines = Int.MAX_VALUE
        tv_origin_text.text = value
        isActivateExpansion = tv_origin_text.lineCount > collapsedLines

        if (!isActivateExpansion || isExpanded || tv_origin_text.text.isNullOrEmpty()) {
            view_gradient.visibility = View.GONE
            return
        }

        tv_origin_text.maxLines = collapsedLines
        view_gradient.visibility = View.VISIBLE
    }

    fun expand() {
        if (!isActivateExpansion || isExpanded || text.isEmpty()) {
            return
        }
        currentState = State.EXPANDED
    }

    fun collapse() {
        if (!isActivateExpansion || isCollapsed || text.isEmpty()) {
            return
        }
        currentState = State.COLLAPSED
    }

    enum class State {
        EXPANDED,
        COLLAPSED,
    }

    companion object {
        const val DEFAULT_MAX_LINES = 4
    }
}