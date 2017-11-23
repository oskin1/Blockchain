import blockchain.models.Block
import blockchain.models.Transaction.Input
import blockchain.models.Transaction.Output
import client.Client
import network.models.messages.BlockMessage
import utils.HASH.sha256
import java.security.KeyPairGenerator
import java.security.Signature
import java.security.spec.ECGenParameterSpec
import com.oracle.util.Checksums.update
import com.fasterxml.jackson.databind.AnnotationIntrospector.pair
import java.math.BigInteger
import java.nio.charset.Charset
import com.oracle.util.Checksums.update
import com.fasterxml.jackson.databind.AnnotationIntrospector.pair
import crypto.KeyManager
import crypto.Sign
import java.security.PrivateKey
import java.security.PublicKey
import java.util.*


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


    val text = "TestMessage"
    val textWrong = "TestMessage"

    val a = KeyManager()
    //println(a.pubKey as PublicKey)
    //val sign = Sign.getSign(a.privKey,text.toByteArray(Charset.forName("UTF-8")))
//    val builder = StringBuilder()
//    for(b in sign){
//        builder.append(b)
//    }
//    println(builder.toString())
    //println(Sign.verifySign(textWrong.toByteArray(Charset.forName("UTF-8")),k,a.pubKey))
//    if(){
//        println("valid")
//    }else{
//        println("invalid")
//    }
    val ramonPK = "ramon"
    val iljaPK = "ilja"

//    val inp = Input("0",0,"0")
//    val out = Output(123, sha256(sha256(iljaPK)))
//    println(inp)
//    println(out)

        // Version Packet packing/unpacking test.

//    val myVm = VersionMessage(1000, InetAddress.getByName("5.23.49.63"), InetAddress.getByName("5.23.49.63"), System.currentTimeMillis())
//    val vmUnp = VersionMessage.unpack(myVm.pack())
//
//    print("\n${vmUnp.version}, ${vmUnp.fromAddress}, ${vmUnp.recvAddress}, ${vmUnp.timestamp}")

}
