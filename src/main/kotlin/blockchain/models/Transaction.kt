package blockchain.models

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

class Transaction(
        val sender: String,
        val recipient: String,
        val amount: Long,
        val timestamp: Long
) : JsonSerializable {

    companion object : JsonLoader<Transaction> {
        override fun jsonLoads(data: ByteArray): Transaction {
            val mapper = jacksonObjectMapper()
            return mapper.readValue(data)
        }
    }

    override fun toString(): String = "<Txn sdr: $sender, rec: $recipient, am: $amount>"

}