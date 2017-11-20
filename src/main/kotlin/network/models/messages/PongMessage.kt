package network.models.messages

class PongMessage(val pingNonce: ByteArray) : Packable {
    val length = 8

    override fun pack(): ByteArray {
        val buffer = ByteArray(1)
        buffer[0] = prefix.toByte()
        return buffer + pingNonce
    }

    companion object : MessageUnpacker<PongMessage>, MessageConstantsHolder {
        override val prefix: Byte = 0x02

        @JvmStatic
        override fun unpack(data: ByteArray): PongMessage {
            return PongMessage(data)
        }
    }

}