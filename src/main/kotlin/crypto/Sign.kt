package crypto

import java.nio.charset.Charset
import java.security.Key
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature

object Sign {

    fun getSign(priKey: PrivateKey, data: ByteArray): ByteArray {

        val ecdsaSign = Signature.getInstance(SignSettings.algorithm);
        ecdsaSign.initSign(priKey)
        ecdsaSign.update(data)
        return ecdsaSign.sign()

    }

    fun verifySign(data: ByteArray, datasign: ByteArray, pubKey: PublicKey): Boolean {

        val escdcaVer = Signature.getInstance(SignSettings.algorithm);
        escdcaVer.initVerify(pubKey)
        escdcaVer.update(data)
        return escdcaVer.verify(datasign)

    }


}