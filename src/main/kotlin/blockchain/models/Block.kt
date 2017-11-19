package blockchain.models

import blockchain.db.Savable
import blockchain.db.pultusORM

import ninja.sakib.pultusorm.core.PultusORMCondition
import ninja.sakib.pultusorm.annotations.AutoIncrement
import ninja.sakib.pultusorm.annotations.PrimaryKey
import ninja.sakib.pultusorm.annotations.Ignore
import ninja.sakib.pultusorm.annotations.Unique

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

class Block(
        // Header.
        val version: Int,
        @Unique val height: Int,
        val timestamp: Long,
        var nonce: Long,
        val prevBlockHash: String,
        val merkleRootHash: String
) : Savable, JsonSerializable {

    @Ignore
    var transactions: List<Transaction> = mutableListOf()
    var transactionsCount: Int = 0

    @PrimaryKey
    @AutoIncrement
    val id: Int = 0

    companion object : JsonLoader<Block> {

        fun dbLoad(height: Int): Block {
            val condition: PultusORMCondition = PultusORMCondition.Builder()
                    .eq("height", height)
                    .build()
            return pultusORM.find(Block, condition)[0] as Block
        }

        override fun jsonLoads(data: ByteArray): Block {
            val mapper = jacksonObjectMapper()
            return mapper.readValue(data)
        }
    }

    override fun toString(): String = "<Block v: $version, h: $height, mrh: $merkleRootHash>"

}