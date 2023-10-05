package knu.dong.capstone2.common

import android.content.Context
import io.ktor.client.plugins.cookies.CookiesStorage
import io.ktor.http.Cookie
import io.ktor.http.CookieEncoding
import io.ktor.http.Url
import knu.dong.capstone2.BuildConfig
import java.io.File

class FileCookieStorage(context: Context): CookiesStorage {
    private val domain: String = BuildConfig.SERVER_URL
    private var file = File(context.filesDir,"cookie.txt")
    private val cookies = mutableMapOf<String, String>()

    init {
        if (!file.exists()) {
            file.createNewFile()
        }

        file.readLines().forEach {
            val (name, value) = it.split(":")
            cookies[name] = value
        }
    }

    override suspend fun addCookie(requestUrl: Url, cookie: Cookie) {
        if (requestUrl.isImportant()) {
            // 쿠키 메모리 갱신
            cookies[cookie.name] = cookie.value
            // 쿠키 파일 갱신
            cookies.map { "${it.key}:${it.value}" }
                .joinToString("\n")
                .also { file.writeText(it) }
        }
    }

    override fun close() {}

    override suspend fun get(requestUrl: Url): List<Cookie> {
        // 인코딩 기본 옵션을 사용하는 경우 퍼센트 인코딩 되어 쿠키 값이 다름
        return cookies.map { Cookie(it.key, it.value, encoding = CookieEncoding.RAW) }
            .takeIf { requestUrl.isImportant() }
            ?: emptyList()
    }

    private fun Url.isImportant() = host == domain // 호스트 하나의 쿠키만 저장하는 경우
}