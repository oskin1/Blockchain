package blockchain

import blockchain.db.models.Block
import blockchain.db.models.Transaction

interface BlockchainDelegate {
    fun newBlockMined(block: Block)
    fun newTransactionCreated(transaction: Transaction)
    fun blockDenied(block: Block)
    fun transactionDenied(transaction: Transaction)
}