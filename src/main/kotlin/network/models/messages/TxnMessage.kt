package network.models.messages

import blockchain.models.Transaction
import java.nio.ByteBuffer
import java.nio.ByteOrder

class TxnMessage(val txn: Transaction) : Packable {

    override fun pack(): ByteArray {
        val blockData = txn.jsonDumps().toByteArray()
        val buffer = ByteBuffer.allocate(blockData.size + 1).apply {
            order(ByteOrder.LITTLE_ENDIAN)
            put(prefix)
            put(blockData, 0, blockData.size)
        }

        return buffer.array()
    }

    companion object : MessageUnpacker<TxnMessage>, MessageConstantsHolder {
        override val prefix: Byte = 0x05

        @JvmStatic
        override fun unpack(data: ByteArray): TxnMessage = TxnMessage(
                Transaction.jsonLoads(data.sliceArray(1..(data.size - 1)))
        )
    }
}