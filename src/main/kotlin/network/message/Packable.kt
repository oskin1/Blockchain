package network.message

interface Packable {
    fun pack(): ByteArray
}