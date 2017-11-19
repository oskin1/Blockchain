package network.message

interface MessageUnpacker<T> {
    fun unpack(data: ByteArray): T
}