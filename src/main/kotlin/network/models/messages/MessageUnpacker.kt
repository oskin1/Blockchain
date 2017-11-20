package network.models.messages

interface MessageUnpacker<T> {
    fun unpack(data: ByteArray): T
}