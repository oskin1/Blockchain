package network.message

class PongMessage(val pingNonce: ByteArray) : DefaultMessage {
    override val prefix: Byte = 0x02
    override val length = 8

    override fun pack(): ByteArray {
        val buffer = ByteArray(1)
        buffer[0] = prefix.toByte()
        return buffer + pingNonce
    }

    companion object {
        @JvmStatic
        fun unpack(data: ByteArray): PongMessage {
            return PongMessage(data)
        }
    }

}