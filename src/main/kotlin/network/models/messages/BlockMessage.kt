package network.models.messages

import blockchain.models.Block
import java.nio.ByteBuffer
import java.nio.ByteOrder

class BlockMessage(val block: Block) : Packable {

    override fun pack(): ByteArray {
        val blockData = block.jsonDumps().toByteArray()
        val buffer = ByteBuffer.allocate(blockData.size + 1).apply {
            order(ByteOrder.LITTLE_ENDIAN)
            put(prefix)
            put(blockData, 0, blockData.size)
        }

        return buffer.array()
    }

    companion object : MessageUnpacker<BlockMessage>, MessageConstantsHolder {
        override val prefix: Byte = 0x04

        @JvmStatic
        override fun unpack(data: ByteArray): BlockMessage = BlockMessage(
                Block.jsonLoads(data.sliceArray(1..(data.size - 1)))
        )
    }
}