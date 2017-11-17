package network

import java.net.InetAddress

data class RemoteNode(val ipAddress: InetAddress, val udpPort: Int, val tcpPort: Int = udpPort) {
    val id: Int? = null
    var version: Int? = null
}