package fr.betclic.domain

/*import org.litote.kmongo.*
import com.mongodb.client.MongoDatabase


//TODO : Make a singleton ?
val client = KMongo.createClient("mongodb://root:root@mongo:27017/") //get com.mongodb.MongoClient new instance
val playerDB: MongoDatabase = client.getDatabase("playerDB")
*/

import org.koin.dsl.module
import org.litote.kmongo.KMongo

val dbModule = module(createdAtStart = true) {
    factory { KMongo.createClient()}
}