package fr.betclic.domain

import com.mongodb.client.MongoCollection

interface RepositoryInterface<T> {

    var col: MongoCollection<T>

    fun getById(id: String): T

    fun getAll(): List<T> {
        return try {
            val res = col.find()
            res.asIterable().map { it }
        } catch (t: Throwable) {
            throw Exception("Cannot get all items")
        }
    }

    fun delete(id: String): Boolean

    fun add(entry: T): T {
        return try {
            col.insertOne(entry)
            entry
        } catch (t: Throwable) {
            throw Exception("Cannot add item")
        }
    }

    fun update(entry: T): T

}