package knu.dong.capstone2.common

import android.content.Context
import android.graphics.Color
import androidx.annotation.AttrRes


fun Context.getThemeColor(@AttrRes themeAttrId: Int): Int {
    return obtainStyledAttributes(
        intArrayOf(themeAttrId)
    ).use {
        it.getColor(0, Color.TRANSPARENT)
    }
}