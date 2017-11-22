import blockchain.models.Block
import blockchain.models.Transaction.Input
import blockchain.models.Transaction.Output
import client.Client
import network.models.messages.BlockMessage
import utils.HASH.sha256

fun main(args: Array<String>) {

    //val client = Client()

    //client.sendCoinsTo(1002, "ivan")
    //client.mine()

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


    val ramonPK = "ramon"
    val iljaPK = "ilja"

    val inp = Input("0",0,"0")
    val out = Output(123, sha256(sha256(iljaPK)))
    println(inp)
    println(out)

        // Version Packet packing/unpacking test.

//    val myVm = VersionMessage(1000, InetAddress.getByName("5.23.49.63"), InetAddress.getByName("5.23.49.63"), System.currentTimeMillis())
//    val vmUnp = VersionMessage.unpack(myVm.pack())
//
//    print("\n${vmUnp.version}, ${vmUnp.fromAddress}, ${vmUnp.recvAddress}, ${vmUnp.timestamp}")

}
