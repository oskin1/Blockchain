package blockchain

import network.NodeDelegate
import utils.HASH.sha256
import blockchain.models.Block
import blockchain.models.Transaction.Transaction

class Blockchain : NodeDelegate {
    private val version = 1  // Version of the BC
    private var mempool = mutableListOf<Transaction>()  // Txn Pool
    private var chain = mutableListOf<Block>()

    private var difficulty = 3  // POW difficulty level

    private lateinit var miningThread: Thread

    var delegate: BlockchainDelegate? = null

    override fun newTransactionReceived(transaction: Transaction) {
        if (validTransaction(transaction)) mempool.add(transaction)
    }

    override fun newBlockReceived(block: Block) {
        if (!commitBlock(block)) delegate?.blockDenied(block)
    }

    fun createBlock(prevBlockHash: String = hash(chain.last()), commit: Boolean = true) {
        // Creates a new Block.
        val block = Block(
                version = version,
                height = chain.size + 1,
                timestamp = System.currentTimeMillis(),
                nonce = 0,  // Initial nonce
                prevBlockHash = prevBlockHash,
                merkleRootHash = merkle(prepareTxnPool(mempool))
        )

        block.transactions = mempool.toList()
        block.transactionsCount = mempool.size

        miningThread = Thread(Runnable {
            // Increment nonce until the valid hash found
            while (!validNonce(block)) block.nonce++

            delegate?.newBlockMined(block)

            if (commit) commitBlock(block)
        }, "MiningThread")
        miningThread.start()
    }

    private fun setupChain() {
        TODO("Not implemented")
    }

    private fun setupMempool() {
        TODO("Not implemented")
    }

    fun commitBlock(block: Block): Boolean {
        // Commits the given Block to the Blockchain.
        if (validChain(chain) && validBlock(block)) {

            mempool.clear()
            chain.add(block)

            // Save the Block to storage.

            print("\nCommitting new block:\n-> $block\n")

            return true
        }
        return false
    }

    @JvmOverloads
    fun addTransaction(sender: String, recipient: String, amount: Long,
                       timestamp: Long = System.currentTimeMillis()): Int {
        // Creates a new transaction to go into the next mined Block.
        val tx = Transaction(sender, amount, timestamp)
        mempool.add(tx)

        // Save the Block to storage.

        println("Adding tx: \n$tx")
        delegate?.newTransactionCreated(tx)

        return getLastBlock().height + 1
    }

    fun getLastBlock(): Block = chain.last()

    private fun resolveConflicts(chain: List<Block>): Boolean {
        if (chain.size > this.chain.size && validChain(chain)) {
            this.chain = chain.toMutableList()
            return true
        }
        return false
    }

    fun adjustDiffRate(): Unit {
        // Not implemented.
    }

    private fun hash(block: Block): String {
        // Creates the SHA-256 hash of Block header.
        var header = block.version.toString(16) + block.height.toString(16) +
                block.timestamp.toString(16) + block.nonce.toString(16) + block.prevBlockHash
        if (block.transactions.isNotEmpty()) header += merkle(prepareTxnPool(block.transactions))

        return sha256(sha256(header))
    }

    private fun hash(transaction: Transaction): String {
        // Creates the SHA-256 hash of Transaction header.
        val header = transaction.sender +
                transaction.amount.toString(16) +
                transaction.timestamp.toString(16)

        return sha256(sha256(header))
    }

    private fun merkle(hashList: List<String>): String {
        if (hashList.isEmpty()) return ""
        if (hashList.size == 1) return hashList[0]
        val newHashList = mutableListOf<String>()
        for (i in 0..(hashList.size - 2) step 2) newHashList.add(hash2(hashList[i], hashList[i+1]))
        if (hashList.size % 2 == 1) newHashList.add(hash2(hashList.last(), hashList.last()))

        return merkle(newHashList)
    }

    private fun hash2(a: String, b: String): String {
        return sha256(sha256(a.reversed() + b.reversed())).reversed()
    }

    private fun prepareTxnPool(transactions: List<Transaction>): List<String> {
        // Returns the list of Transactions hashes.
        return transactions.map { hash(it) }
    }

    private fun validNonce(block: Block): Boolean {
        // Validates the given nonce.
        return hash(block).slice(0..(difficulty - 1)) == "0".repeat(difficulty)
    }

    private fun validTransaction(txn: Transaction): Boolean {
        // Not implemented.
        return true
    }

    private fun validBlock(block: Block): Boolean {
        // Validates the given block.
        val prevBlock: Block? = if (block.height > 1) chain[block.height - 2] else null
        if (prevBlock != null) {
            if ((block.height - prevBlock.height) != 1) return false
            if (block.version < prevBlock.version) return false
            if (block.prevBlockHash != hash(prevBlock)) return false
            if (block.merkleRootHash != merkle(prepareTxnPool(block.transactions))) return false
            if (!validNonce(block)) return false
        } else {
            if (chain.isNotEmpty() && hash(block) != hash(chain[0])) return false
        }
        return true
    }

    private fun validChain(chain: List<Block>): Boolean {
        // Validates the given chain.
        var currentIndex = 1

        while (currentIndex < chain.size) {
            val block = chain[currentIndex]

            if (!validBlock(block)) return false

            currentIndex++
        }
        return true
    }

    fun createGenesisBlock() {
        // Temporary
        val block = Block(
                version = version,
                height = chain.size + 1,
                timestamp = System.currentTimeMillis(),
                nonce = 0,  // Initial nonce
                prevBlockHash = "100",
                merkleRootHash = merkle(prepareTxnPool(mempool))
        )

        block.transactions = mempool.toList()
        block.transactionsCount = mempool.size

        while (!validNonce(block)) block.nonce++
        commitBlock(block)
    }

}