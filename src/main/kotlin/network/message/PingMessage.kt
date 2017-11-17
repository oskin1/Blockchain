package network.message

import java.util.*

class PingMessage : DefaultMessage {
    override val prefix: Byte = 0x01
    override val length = 8

    var nonce = ByteArray(7)

    init {
        Random().nextBytes(nonce)
    }

    override fun pack(): ByteArray {
        val buffer = ByteArray(1)
        buffer[0] = prefix.toByte()
        return buffer + nonce
    }

    companion object {
        @JvmStatic
        fun unpack(data: ByteArray): PingMessage {
            val instance = PingMessage()
            instance.nonce = data
            return instance
        }
    }
}