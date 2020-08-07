package com.alessandro.co2chart.util

import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment

object ColorUtil {

    fun Fragment.getColor(@ColorRes colorId: Int): Int = this.resources.getColor(colorId, null)
}