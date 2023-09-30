package knu.dong.capstone2.common

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readText
import io.ktor.http.HttpStatusCode
import knu.dong.capstone2.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HttpRequestHelper {
    private val client: HttpClient = HttpClient(CIO)
    private val domain: String = BuildConfig.SERVER_URL

    suspend fun get(path: String): String =
        withContext(Dispatchers.IO) {
            try {
                val url = "$domain/$path"

                val response: HttpResponse = client.get(url)
                val responseStatus = response.status

                if (responseStatus == HttpStatusCode.OK) {
                    response.readText()
                } else {
                    "error: $responseStatus"
                }
            }
            catch (err: Exception) {
                Log.e("dong", err.toString())

                "error: $err"
            }
        }
}