package knu.dong.capstone2.common

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import androidx.annotation.AttrRes


fun Context.getThemeColor(@AttrRes themeAttrId: Int): Int {
    return obtainStyledAttributes(
        intArrayOf(themeAttrId)
    ).use {
        it.getColor(0, Color.TRANSPARENT)
    }
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()