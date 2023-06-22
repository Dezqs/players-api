package fr.betclic.services

import com.mongodb.client.MongoClient
import fr.betclic.domain.dto.PlayerDTO
import fr.betclic.domain.game.Game
import fr.betclic.domain.game.GameRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.stream.Collectors
import java.util.stream.Collectors.summingInt

class PlayerService : KoinComponent {

    private val client: MongoClient by inject()
    private val gameRepository: GameRepository = GameRepository(client)

    fun getPlayerByPseudo(pseudo: String): PlayerDTO {
        return try {
            aggregatePlayerByTournament(gameRepository.findGamesByUser(pseudo))
        } catch (t: Throwable) {
            throw Exception(t.message, t)
        }
    }

    /**
     * Doesn't work actually FIXME
     */
    fun getPlayerByPseudoWithMongoAggregation(pseudo: String): PlayerDTO {
        try {
            val gamesMap = HashMap<String, Int>()
            gameRepository.aggregateGamesByUser(pseudo)
                ?.forEach { gamesMap[it.tournament] = it.points }
            return PlayerDTO(pseudo, gamesMap)
        } catch (t: Throwable) {
            throw Exception(t.message, t)
        }
    }

    fun getAllPlayers(): List<PlayerDTO> {
        val players = ArrayList<PlayerDTO>()
        gameRepository.getAll().stream()
            .collect(Collectors.groupingBy(Game::pseudo))
            .values.stream().forEach { players.add(aggregatePlayerByTournament(it)) }

        return players
    }

    private fun aggregatePlayerByTournament(playerGames: List<Game>) : PlayerDTO{
        val mapTournament = playerGames.stream()
            .collect(Collectors.groupingBy(Game::tournament, summingInt(Game::points)))
        if(mapTournament.isNotEmpty())
            return PlayerDTO(playerGames[0].pseudo, mapTournament)
        else
            throw Exception ("no player found for this pseudo")
    }

    fun deleteUser(pseudo: String): Boolean {
        return gameRepository.deleteAllGameByPseudo(pseudo)
    }


}