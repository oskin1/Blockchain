package client

import blockchain.Blockchain
import crypto.KeyManager
import network.Node
import utils.toHexString
import java.util.*

class Client {
    private val addr: String = "9fec0eec184712d2e3d95a"  //KeyManager().pubKey.encoded.toHexString()
    private val blockchain: Blockchain = Blockchain()
    private val node: Node = Node()

    private var isInitilLaunch: Boolean = true

    init {
        if (isInitilLaunch) blockchain.createGenesisBlock()
        blockchain.delegate = node
        node.delegate = blockchain
        node.startup()
    }

    fun sendCoinsTo(amount: Long, recipient: String) {
        blockchain.addTransaction(addr, recipient, amount)
    }

    fun mine() {
        blockchain.addTransaction("coinbase", addr, 24)
        blockchain.createBlock()
    }
}