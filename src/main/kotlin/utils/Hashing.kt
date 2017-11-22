package utils

import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom


private object Helper {

    fun getRawBytes(text: String): ByteArray {
        try {
            return text.toByteArray(Charsets.UTF_8)
        } catch (e: UnsupportedEncodingException) {
            return text.toByteArray()
        }
    }

    fun getString(data: ByteArray): String {
        try {
            return String(data, Charsets.UTF_8)
        } catch (e: UnsupportedEncodingException) {
            return String(data)
        }

    }

}

object HASH {
    private val MD5 = "MD5"
    private val SHA_1 = "SHA-1"
    private val SHA_256 = "SHA-256"
    private val SHA_512 = "SHA-512"
    private val DIGITS_LOWER = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')
    private val DIGITS_UPPER = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

    fun md5(data: ByteArray): String {
        return String(encodeHex(md5Bytes(data)))
    }

    fun md5(text: String): String {
        return String(encodeHex(md5Bytes(Helper.getRawBytes(text))))
    }

    fun md5Bytes(data: ByteArray): ByteArray {
        return getDigest(MD5).digest(data)
    }

    fun sha1(data: ByteArray): String {
        return String(encodeHex(sha1Bytes(data)))
    }

    fun sha1(text: String): String {
        return String(encodeHex(sha1Bytes(Helper.getRawBytes(text))))
    }

    fun sha1Bytes(data: ByteArray): ByteArray {
        return getDigest(SHA_1).digest(data)
    }

    fun sha256(data: ByteArray): String {
        return String(encodeHex(sha256Bytes(data)))
    }

    fun sha256(text: String): String {
        return String(encodeHex(sha256Bytes(Helper.getRawBytes(text))))
    }

    fun sha256Bytes(data: ByteArray): ByteArray {
        return getDigest(SHA_256).digest(data)
    }

    fun sha512(data: ByteArray): String {
        return String(encodeHex(sha256Bytes(data)))
    }

    fun sha512(text: String): String {
        return String(encodeHex(sha256Bytes(Helper.getRawBytes(text))))
    }

    fun sha512Bytes(data: ByteArray): ByteArray {
        return getDigest(SHA_256).digest(data)
    }

    fun getDigest(algorithm: String): MessageDigest {
        try {
            return MessageDigest.getInstance(algorithm)
        } catch (e: NoSuchAlgorithmException) {
            throw IllegalArgumentException(e)
        }

    }

    fun encodeHex(data: ByteArray, toLowerCase: Boolean = true): CharArray {
        return encodeHex(data, if (toLowerCase) DIGITS_LOWER else DIGITS_UPPER)
    }

    fun encodeHex(data: ByteArray, toDigits: CharArray): CharArray {
        val l = data.size
        val out = CharArray(l shl 1)
        var i = 0
        var j = 0
        while (i < l) {
            out[j++] = toDigits[(240 and data[i].toInt()).ushr(4)]
            out[j++] = toDigits[15 and data[i].toInt()]
            i++
        }
        return out
    }

}