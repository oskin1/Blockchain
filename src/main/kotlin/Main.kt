import blockchain.models.Block
import client.Client
import network.models.messages.BlockMessage

fun main(args: Array<String>) {

    val client = Client()

    client.sendCoinsTo(1002, "ivan")
    client.mine()

        // Blockchain test.

//    Blockchain().apply {
//        addTransaction("me", "Mike", 32)
//        createBlock(commit = true)
//
//        addTransaction("me", "Ivan", 32)
//        addTransaction("me", "Kostya", 32)
//        createBlock(commit = true)
//
//        addTransaction("me", "Angela", 10)
//        addTransaction("me", "Sonya", 32)
//        createBlock(commit = true)
//
//        addTransaction("me", "Viki", 325)
//        createBlock(commit = true)
//
//        addTransaction("me", "Tom", 325)
//        createBlock(commit = true)
//    }


        // Node Test.

//    val myNode = Node()


        // Version Packet packing/unpacking test.

//    val myVm = VersionMessage(1000, InetAddress.getByName("5.23.49.63"), InetAddress.getByName("5.23.49.63"), System.currentTimeMillis())
//    val vmUnp = VersionMessage.unpack(myVm.pack())
//
//    print("\n${vmUnp.version}, ${vmUnp.fromAddress}, ${vmUnp.recvAddress}, ${vmUnp.timestamp}")

}
