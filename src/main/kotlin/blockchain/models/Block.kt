package blockchain.models

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

class Block(
        // Header.
        val version: Int,
        val height: Int,
        val timestamp: Long,
        var nonce: Long,
        val prevBlockHash: String,
        val merkleRootHash: String
) : JsonSerializable {

    var transactions: List<Transaction> = mutableListOf()
    var transactionsCount: Int = 0

    companion object : JsonLoader<Block> {
        override fun jsonLoads(data: ByteArray): Block {
            val mapper = jacksonObjectMapper()
            return mapper.readValue(data)
        }
    }

    override fun toString(): String = "<Block v: $version, h: $height, mrh: $merkleRootHash>"

}