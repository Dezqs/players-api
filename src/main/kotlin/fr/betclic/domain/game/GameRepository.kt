package fr.betclic.domain.game

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import fr.betclic.domain.RepositoryInterface
import org.litote.kmongo.*


class GameRepository(client: MongoClient) : RepositoryInterface<Game> {

    override lateinit var col: MongoCollection<Game>

    init {
        val database = client.getDatabase("playerDB")
        col = database.getCollection<Game>("Game")

    }

    fun deleteAllGameByPseudo(pseudo: String): Boolean {
        return try {
            col.deleteMany(Game::pseudo eq pseudo).deletedCount > 0
        } catch (e: Exception) {
            throw Exception("Unable to delete the player $pseudo", e)
        }
    }

    /**
     * Doesn't work for now but it's a proper way to aggregate data
     */
    fun aggregateGamesByUser(pseudo: String): List<Game>? {
        return try {
            col.aggregate<Game>(
                match(Game::pseudo eq pseudo),
                project(
                    exclude(Game::id),
                    Game::pseudo from Game::pseudo,
                    Game::tournament from Game::tournament,
                    Game::points from Game::points
                ),
                group(
                    and(Game::pseudo from Game::pseudo, Game::tournament from Game::tournament),
                    Game::points sum Game::points
                )
            ).toList()
        } catch (t: Throwable) {
            throw Exception("Cannot get item", t)

        }
    }

    fun findGamesByUser(pseudo: String): List<Game> {
        return try {
            col.find(Game::pseudo eq pseudo).toList()
        } catch (t: Throwable) {
            throw Exception("Cannot get item", t)

        }
    }

    fun findGamesByTournament(tournament: String): List<Game> {
        return try {
            col.find(Game::tournament eq tournament).toList()
        } catch (t: Throwable) {
            throw Exception("Cannot get item", t)

        }
    }

    fun deleteTournament(tournamentName: String): Boolean {
        return try {
            col.deleteMany(Game::tournament eq tournamentName).deletedCount > 0
        } catch (e: Exception) {
            throw Exception("Unable to delete the tournament $tournamentName", e)
        }

    }

}