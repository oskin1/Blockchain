package network

import blockchain.db.models.Block
import blockchain.db.models.Transaction

interface NodeDelegate {
    fun newBlockReceived(block: Block)
    fun newTransactionReceived(transaction: Transaction)
}