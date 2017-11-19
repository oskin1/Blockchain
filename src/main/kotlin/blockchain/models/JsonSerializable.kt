package blockchain.models

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

interface JsonSerializable {
    fun jsonDumps(): String {
        val mapper = jacksonObjectMapper()
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this)
    }
}