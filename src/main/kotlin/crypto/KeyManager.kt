package crypto

import java.io.File
import java.security.*
import java.security.spec.ECGenParameterSpec
import java.security.PrivateKey
import java.security.PublicKey
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import java.security.spec.PKCS8EncodedKeySpec




class KeyManager {
    var privKey: PrivateKey private set
    var pubKey: PublicKey private set
    init {
        try {
            println("Reading data from files")
            privKey = getPrivKeyFromFile()
            pubKey = getPubKeyFromFile()
        } catch (t: Throwable) {
            println("failed")
            val keyPair = generateKeyPair()

            privKey = keyPair.private
            pubKey = keyPair.public

            saveToFile(privKey, KeySettings.privKeyFileName)
            saveToFile(pubKey, KeySettings.pubKeyFileName)
        }
    }

    private fun generateKeyPair(): KeyPair {
        val keyGen = KeyPairGenerator.getInstance(KeySettings.algotithmSpec)
        val kpgparams = ECGenParameterSpec(KeySettings.ParameterSpec);
        keyGen.initialize(kpgparams);

        val pair = keyGen.generateKeyPair();

        return pair
    }

    private fun saveToFile(key: Key, keyFileName: String) {
        val file = File(KeySettings.keysDirectoryPath + keyFileName)
        val parent = file.parentFile
        if (!parent.exists() && !parent.mkdirs()) {
            throw IllegalStateException("Failed to create dirs up to ${file.parent}")
        }

        var byteArr = key.encoded

        file.writeBytes(byteArr)
    }

    private fun tryReadFromFile(keyFileName: String): ByteArray {
        val file = File(keyFileName)
        return  file.readBytes()
    }

    private fun getPrivKeyFromFile() : PrivateKey{

        val buffer = tryReadFromFile(KeySettings.keysDirectoryPath + KeySettings.privKeyFileName)

        val formatted_private = PKCS8EncodedKeySpec(buffer)

        val kf = KeyFactory.getInstance(KeySettings.algotithmSpec)

        return kf.generatePrivate(formatted_private)
    }

    private fun getPubKeyFromFile() : PublicKey{

        val buffer = tryReadFromFile(KeySettings.keysDirectoryPath + KeySettings.pubKeyFileName)

        val formatted_public = X509EncodedKeySpec(buffer)

        val kf = KeyFactory.getInstance(KeySettings.algotithmSpec)

        return kf.generatePublic(formatted_public)

    }
}