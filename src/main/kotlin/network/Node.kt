package network

import blockchain.BlockchainDelegate
import crypto.KeyManager
import java.io.IOException
import java.net.*

import blockchain.models.Block
import blockchain.models.Transaction
import network.models.RemoteNode
import network.models.messages.*

class Node(private val udpPort: Int = NodeSettings.defaultUdpPort) : BlockchainDelegate {
    private lateinit var socket: DatagramSocket
    private lateinit var listeningThread: Thread
    private lateinit var broadcastingThread: Thread

    private val privateKey = KeyManager().privKey

    private var neighbours = mutableListOf<RemoteNode>()
    private var blacklist = mutableListOf<RemoteNode>()
    private var active = false

    var delegate: NodeDelegate? = null

    override fun newBlockMined(block: Block) {
        broadcast(BlockMessage(block).pack())
    }

    override fun newTransactionCreated(transaction: Transaction) {
        broadcast(TxnMessage(transaction).pack())
    }

    override fun blockDenied(block: Block) {
        TODO("not implementeded") //To change body of created functions use File | Settings | File Templates.
    }

    override fun transactionDenied(transaction: Transaction) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun startup() {
        for (port in NodeSettings.defaultPortList) {
            try {
                socket = DatagramSocket(port)
            } catch (e: SocketException) {
                e.printStackTrace()
                continue
            }
            active = true
            break
        }

        udpListen()
        println("Running OC node on ${socket.localAddress}:${socket.localPort}")
    }

    fun shutDown() {
        active = false
    }

    private fun udpListen() {
        listeningThread = Thread(Runnable {
            while (active) {
                receive()
            }
        }, "udpListeningThread")
        listeningThread.start()
    }

    private fun receive() {
        val buffer = ByteArray(1024)
        val packet = DatagramPacket(buffer, buffer.size)

        try {
            socket.receive(packet)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        dispatch(packet)
    }

    private fun dispatch(packet: DatagramPacket) {

        // TODO: Add packet signature verification.

        val data = packet.data.sliceArray(0..(packet.length - 1))
        val remoteNode = RemoteNode(packet.address, packet.port)

        // Tests the Node.
        if (remoteNode in blacklist) return
        if (remoteNode !in neighbours) neighbours.add(remoteNode)

        // Defines the packet type and decides how to treat it.
        when (data[0]) {
            PingMessage.prefix      -> sendTo(PongMessage(PingMessage.unpack(data).nonce).pack(), remoteNode)
            PongMessage.prefix      -> print("\nReceived messages of type Pong")
            VersionMessage.prefix   -> if (VersionMessage.unpack(data).recvAddress == socket.localAddress) {
                sendTo(VersionMessage(NodeSettings.protocolVersion, packet.address,
                        socket.localAddress, System.currentTimeMillis()).pack(), remoteNode) }
            BlockMessage.prefix     -> delegate?.newBlockReceived(BlockMessage.unpack(data).block)
            TxnMessage.prefix       -> delegate?.newTransactionReceived(TxnMessage.unpack(data).txn)
        }
    }

    private fun broadcast(data: ByteArray) {
        println("Broadcasting to ${neighbours.size} nodes...")
        broadcastingThread = Thread(Runnable {
            for (node in neighbours) {
                sendTo(data, node)
                println("Broadcasting to ${node.ipAddress}:${node.udpPort}")
            }
        }, "broadcastingThread")
        broadcastingThread.start()
    }

    fun sendTo(data: ByteArray, remoteNode: RemoteNode) {
        val packet = DatagramPacket(data, data.size, remoteNode.ipAddress, remoteNode.udpPort)
        try {
            socket.send(packet)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun wrapPacket() {
        // Signs the packet
    }

}