package blockchain.models.Transaction

import blockchain.models.JsonSerializable

class Input(
        val prevTranscationHash : String,
        val prevOutputIndex : Short,
        val unlock : String
) : JsonSerializable
{
    override fun toString(): String = "Input { prevTranscationHash = $prevTranscationHash , prevOutputIndex = $prevOutputIndex, unlock = $unlock}"
}

