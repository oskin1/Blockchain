package blockchain.db

import ninja.sakib.pultusorm.exceptions.PultusORMException

interface Savable {
    fun save(){
        try {
            pultusORM.save(this)
        } catch (e: PultusORMException) {
            e.printStackTrace()
        }
    }
}