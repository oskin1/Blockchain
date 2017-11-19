package network

import java.net.InetAddress

data class RemoteNode(val ipAddress: InetAddress, val udpPort: Int, val tcpPort: Int = udpPort) {
    val id: Int? = null
    var version: Int? = null

    override fun equals(other: Any?): Boolean {
        if (other !is RemoteNode) return false
        return this.ipAddress == other.ipAddress && this.udpPort == other.udpPort
    }

    override fun hashCode(): Int {
        var result = ipAddress.hashCode()
        result = 31 * result + udpPort
        result = 31 * result + tcpPort
        result = 31 * result + (id ?: 0)
        result = 31 * result + (version ?: 0)
        return result
    }
}