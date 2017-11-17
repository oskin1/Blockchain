package blockchain.db.models

import blockchain.db.pultusORM
import ninja.sakib.pultusorm.annotations.AutoIncrement
import ninja.sakib.pultusorm.annotations.PrimaryKey
import ninja.sakib.pultusorm.exceptions.PultusORMException

class Transaction(
        val sender: String,
        val recipient: String,
        val amount: Long,
        val timestamp: Long
) {
    // Db representation.
    @PrimaryKey
    @AutoIncrement
    val id: Int = 0

    fun save(){
        try {
            pultusORM.save(this)
            pultusORM.close()
        } catch (e: PultusORMException) {
            e.printStackTrace()
        }
    }
}