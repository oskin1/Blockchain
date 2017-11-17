package network.message

import utils.toHexString
import java.net.InetAddress
import java.nio.ByteBuffer
import java.nio.ByteOrder


class VersionMessage(val version: Int, val recvAddress: InetAddress,
                     val fromAddress: InetAddress, val timestamp: Long) : DefaultMessage {
    override val prefix: Byte = 0x03
    override val length = 64

    override fun pack(): ByteArray {
        val buffer = ByteBuffer.allocate(21)
        buffer.order(ByteOrder.LITTLE_ENDIAN)
        buffer.put(prefix)
        buffer.putInt(version)
        buffer.putLong(timestamp)
        buffer.put(fromAddress.address, 0, fromAddress.address.size)
        buffer.put(recvAddress.address, 0, recvAddress.address.size)

        println("${buffer.array().toHexString()}\n")

        return buffer.array()
    }

    companion object {
        @JvmStatic
        fun unpack(data: ByteArray): VersionMessage {
            val version = ByteBuffer.wrap(data.sliceArray(1..4)).order(ByteOrder.LITTLE_ENDIAN)
            val timestamp = ByteBuffer.wrap(data.sliceArray(5..12)).order(ByteOrder.LITTLE_ENDIAN)
            return VersionMessage(version.int, InetAddress.getByAddress(data.sliceArray(13..16)),
                    InetAddress.getByAddress(data.sliceArray(17..20)), timestamp.long)
        }
    }
}