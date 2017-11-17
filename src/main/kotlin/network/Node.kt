package network

import blockchain.BlockchainDelegate
import crypto.KeyManager
import network.message.PingMessage
import network.message.PongMessage
import network.message.VersionMessage
import java.io.IOException
import java.net.*

import blockchain.db.models.Block
import blockchain.db.models.Transaction

class Node(private val udpPort: Int = NodeSettings.defaultUdpPort): BlockchainDelegate {
    private lateinit var socket: DatagramSocket
    private lateinit var nodeThread: Thread
    private lateinit var listeningThread: Thread
    private lateinit var sendingThread: Thread

    private val privateKey = KeyManager().privKey

    private var neighbours = mutableListOf<RemoteNode>()
    private var active = false

    var delegate: NodeDelegate? = null

    override fun newBlockMined(block: Block) {
        println("Node has been notified of the new Block")
    }

    override fun newTransactionCreated(transaction: Transaction) {
        println("Node has been notified of the new TXN")
    }

    override fun blockDenied(block: Block) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun transactionDenied(transaction: Transaction) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun startup() {
        try {
            socket = DatagramSocket(udpPort)
        } catch (e: SocketException) {
            e.printStackTrace()
            return
        }
        nodeThread = Thread(Runnable {
            active = true
            udpListen()

            println("Running OC node on ${socket.localAddress}:${socket.localPort}")
        }, "nodeThread")
        nodeThread.start()
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

        process(packet)
    }

    private fun process(packet: DatagramPacket) {
        // Verifies the given packet
        // Defines the packet type and decides how to treat it.
        val data = packet.data.sliceArray(0..(packet.length - 1))
        val remoteNode = RemoteNode(packet.address, packet.port)
        when (data[0].toInt()) {
            1 -> sendTo(PongMessage(PingMessage.unpack(data).nonce).pack(), remoteNode)  // Ping -> Sending Pong
            2 -> print("\nReceived message of type Pong")
            3 -> if (VersionMessage.unpack(data).recvAddress == socket.localAddress) {
                sendTo(VersionMessage(NodeSettings.protocolVersion, packet.address,
                        socket.localAddress, System.currentTimeMillis()).pack(), remoteNode)
            }
        }
    }

    fun sendTo(data: ByteArray, remoteNode: RemoteNode) {
        sendingThread = Thread(Runnable {
            val packet = DatagramPacket(data, data.size, remoteNode.ipAddress, remoteNode.udpPort)
            try {
                socket.send(packet)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }, "sendingThread")
        sendingThread.start()
    }

    private fun wrapPacket() {
        // Signs the packet
    }

}