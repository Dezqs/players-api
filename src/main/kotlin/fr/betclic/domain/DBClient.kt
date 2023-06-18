package fr.betclic.domain

import org.koin.dsl.module
import org.litote.kmongo.KMongo

val dbModule = module(createdAtStart = true) {
    factory { KMongo.createClient("mongodb://root:root@localhost:27017/") }
}