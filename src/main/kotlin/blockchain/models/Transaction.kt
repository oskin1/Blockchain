package blockchain.models

import blockchain.db.Savable
import blockchain.db.pultusORM

import ninja.sakib.pultusorm.annotations.AutoIncrement
import ninja.sakib.pultusorm.annotations.PrimaryKey
import ninja.sakib.pultusorm.core.PultusORMCondition

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

class Transaction(
        val sender: String,
        val recipient: String,
        val amount: Long,
        val timestamp: Long
) : Savable, JsonSerializable {

    @PrimaryKey
    @AutoIncrement
    val id: Int = 0

    companion object : JsonLoader<Transaction> {

        fun dbLoad(pk: Int): Transaction {
            val condition: PultusORMCondition = PultusORMCondition.Builder()
                    .eq("id", pk)
                    .build()
            return pultusORM.find(Transaction, condition)[0] as Transaction
        }

        override fun jsonLoads(data: ByteArray): Transaction {
            val mapper = jacksonObjectMapper()
            return mapper.readValue(data)
        }
    }

    override fun toString(): String = "<Txn sdr: $sender, rec: $recipient, am: $amount>"

}