package blockchain

import blockchain.models.Block
import blockchain.models.Transaction.Transaction

interface BlockchainDelegate {
    fun newBlockMined(block: Block)
    fun newTransactionCreated(transaction: Transaction)
    fun blockDenied(block: Block)
    fun transactionDenied(transaction: Transaction)
}