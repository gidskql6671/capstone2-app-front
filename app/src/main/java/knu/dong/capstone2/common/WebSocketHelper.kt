package knu.dong.capstone2.common

import io.socket.client.IO
import io.socket.client.Socket
import knu.dong.capstone2.BuildConfig

class WebSocketHelper {
    private val websocketHost: String = BuildConfig.WEBSOCKET_HOST
    private val websocketPort: Int = BuildConfig.WEBSOCKET_PORT

    val socket : Socket = IO.socket("$websocketHost:$websocketPort")
}