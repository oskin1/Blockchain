package blockchain.models

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

interface JsonLoader<T> {
    fun jsonLoads(data: ByteArray): T
}