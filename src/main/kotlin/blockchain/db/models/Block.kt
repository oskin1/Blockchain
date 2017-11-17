package blockchain.db.models

import blockchain.db.pultusORM
import ninja.sakib.pultusorm.core.PultusORMCondition
import ninja.sakib.pultusorm.annotations.AutoIncrement
import ninja.sakib.pultusorm.annotations.PrimaryKey
import ninja.sakib.pultusorm.annotations.Ignore
import ninja.sakib.pultusorm.annotations.Unique
import ninja.sakib.pultusorm.exceptions.PultusORMException

class Block(
        // Header.
        val version: Int,
        @Unique val height: Int,
        val timestamp: Long,
        var nonce: Long,
        val prevBlockHash: String,
        val merkleRootHash: String
) {
    // Txn.
    @Ignore
    var transactions: List<Transaction> = mutableListOf()
    var transactionsCount: Int = 0

    // Db representation.
    @PrimaryKey
    @AutoIncrement
    val id: Int = 0

    fun save(){
        try {
            pultusORM.save(this)
            pultusORM.close()
        } catch (e: PultusORMException) {
            e.printStackTrace()
        }
    }

    companion object {
        fun get(height: Int): Block {
            val condition: PultusORMCondition = PultusORMCondition.Builder()
                    .eq("height", height)
                    .build()
            return pultusORM.find(Block, condition)[0] as Block
        }
    }

}