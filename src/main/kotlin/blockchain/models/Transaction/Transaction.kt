package blockchain.models.Transaction

import blockchain.models.JsonLoader
import blockchain.models.JsonSerializable
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

class Transaction(
        val sender: String,
        val amount: Long,
        val timestamp: Long,
        val input : Array<Input> = emptyArray(),
        val output : Array<Output> = emptyArray()
) : JsonSerializable {

    companion object : JsonLoader<Transaction> {
        override fun jsonLoads(data: ByteArray): Transaction {
            val mapper = jacksonObjectMapper()
            return mapper.readValue(data)
        }
    }

    override fun toString(): String = "<Txn sdr: $sender, am: $amount>"

}