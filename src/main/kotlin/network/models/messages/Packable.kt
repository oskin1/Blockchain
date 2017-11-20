package network.models.messages

interface Packable {
    fun pack(): ByteArray
}