package ltd.matrixstudios.alchemist

import ltd.matrixstudios.mongo.MongoDataFlow
import ltd.matrixstudios.mongo.credientials.MongoPoolConnection

object Alchemist {

    lateinit var dataflow: MongoDataFlow

    fun start(poolConnection: MongoPoolConnection) {
        dataflow = MongoDataFlow().of().setPool(poolConnection).setDatabase("Alchemist")


    }
}