package blockchain.models.Transaction

import blockchain.models.JsonSerializable

class Input(
        val prevTranscationHash : String,
        val prevOutputIndex : Short,
        val publKey : String,
        val transactionOwnerSign : Byte
) : JsonSerializable
{
    override fun toString(): String = "Input { prevTranscationHash = $prevTranscationHash , prevOutputIndex = $prevOutputIndex}"
}

