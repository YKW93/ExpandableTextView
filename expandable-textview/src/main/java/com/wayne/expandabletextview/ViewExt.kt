package com.wayne.expandabletextview

import android.view.View

internal fun View.dpToPx(dp: Int): Float {
    val scale = resources.displayMetrics.density
    return dp * scale
}