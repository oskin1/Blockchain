package crypto

import java.security.Key
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.io.File


class KeyManager {
    var privKey: Key private set
    var pubKey: Key private set

    init {
        try {
            privKey = tryReadFromFile(KeySettings.privKeyFileName)
            pubKey = tryReadFromFile(KeySettings.pubKeyFileName)
        } catch (t: Throwable) {
            val keyPair = generateKeyPair()

            privKey = keyPair.private
            pubKey = keyPair.public

            saveToFile(privKey, KeySettings.privKeyFileName)
            saveToFile(pubKey, KeySettings.pubKeyFileName)
        }
    }

    private fun generateKeyPair(): KeyPair {
        val keyGen = KeyPairGenerator.getInstance(KeySettings.algotithmSpec)

        return keyGen.genKeyPair()
    }

    private fun saveToFile(key: Key, keyFileName: String) {
        val file = File(KeySettings.keysDirectoryPath + keyFileName)
        val parent = file.parentFile
        if (!parent.exists() && !parent.mkdirs()) {
            throw IllegalStateException("Failed to create dirs up to ${file.parent}")
        }

        file.writeBytes(key.encoded)
    }

    private fun tryReadFromFile(keyFileName: String): Key {
        val keyDeserializer = DefaultKeyDeserializer()
        val file = File(keyFileName)
        val bytes = file.readBytes()

        return keyDeserializer.deserialize(bytes)
    }
}