package fr.betclic.domain.player

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import fr.betclic.domain.RepositoryInterface
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection

class PlayerRepository(client: MongoClient) : RepositoryInterface<Player> {

    override lateinit var col: MongoCollection<Player>

    init {
        val database = client.getDatabase("playerDB")
        col = database.getCollection<Player>("Player")

    }

    override fun getById(id: String): Player {
        TODO("Not yet implemented")
    }

    override fun delete(id: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun update(entry: Player): Player {
        TODO("Not yet implemented")
    }

    fun findUser(pseudo: String): Player? {
        return try {
            col.findOne(Player::pseudo eq pseudo) ?: throw Exception("No item with that ID exists")
        } catch (t: Throwable) {
            throw Exception("Cannot get item")
        }
    }

}