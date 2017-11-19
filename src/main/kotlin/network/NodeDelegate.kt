package network

import blockchain.models.Block
import blockchain.models.Transaction

interface NodeDelegate {
    fun newBlockReceived(block: Block)
    fun newTransactionReceived(transaction: Transaction)
}