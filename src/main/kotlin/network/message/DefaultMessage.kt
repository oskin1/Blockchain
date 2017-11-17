package network.message

interface DefaultMessage {
    val prefix: Byte
    val length: Int

    fun pack(): ByteArray
}