package knu.dong.capstone2.common

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import androidx.annotation.AttrRes
import io.ktor.client.statement.HttpResponse
import java.io.Serializable


fun Context.getThemeColor(@AttrRes themeAttrId: Int): Int {
    return obtainStyledAttributes(
        intArrayOf(themeAttrId)
    ).use {
        it.getColor(0, Color.TRANSPARENT)
    }
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()


// API 33 이후 getSerializable()가 deprecated 되어서 만든 확장함수
fun <T: Serializable> Intent.getSerializable(key: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getSerializableExtra(key, clazz)
    } else {
        this.getSerializableExtra(key) as T?
    }
}

fun HttpResponse.ok(): Boolean = this.status.value.div(100) == 2