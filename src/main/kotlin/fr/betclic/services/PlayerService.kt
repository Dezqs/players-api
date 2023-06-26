package fr.betclic.services

import com.mongodb.client.MongoClient
import fr.betclic.domain.dto.PlayerTournamentAggregation
import fr.betclic.domain.dto.RankedPlayerDTO
import fr.betclic.domain.dto.RankedTournamentDTO
import fr.betclic.domain.game.Game
import fr.betclic.domain.game.GameRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.stream.Collectors
import java.util.stream.Collectors.summingInt
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class PlayerService : KoinComponent {

    private val client: MongoClient by inject()
    private val gameRepository: GameRepository = GameRepository(client)
    private val tournamentService: TournamentService by inject()

    fun getPlayerByPseudo(pseudo: String): RankedPlayerDTO {
        return try {
            enrichPlayerWithTournamentRanking(aggregatePlayerByTournament(gameRepository.findGamesByUser(pseudo)))
        } catch (t: Throwable) {
            throw Exception(t.message, t)
        }
    }

    private fun enrichPlayerWithTournamentRanking(player: PlayerTournamentAggregation): RankedPlayerDTO {
        val listRankedTournamentDTO = mutableListOf<RankedTournamentDTO>()
        player.games.keys.forEach { tournament ->
            if (!tournament.isNullOrEmpty()) {
                val playersRanked = tournamentService.getTournamentPlayersOrderedByPoints(tournament)
                var ranking = 0
                val tournamentItem = playersRanked.stream()
                    .filter { it.pseudo == player.pseudo }
                    .findFirst()
                if (tournamentItem.isPresent) {
                    ranking = playersRanked.indexOf(tournamentItem.get()) + 1
                }
                listRankedTournamentDTO.add(
                    RankedTournamentDTO(
                        tournament,
                        ranking,
                        player.games.getOrDefault(tournament, 0)
                    )
                )
            }
        }
        return RankedPlayerDTO(player.pseudo, listRankedTournamentDTO)
    }

    /**
     * Doesn't work actually FIXME
     */
    fun getPlayerByPseudoWithMongoAggregation(pseudo: String): PlayerTournamentAggregation {
        try {
            val gamesMap = HashMap<String?, Int>()
            gameRepository.aggregateGamesByUser(pseudo)
                ?.forEach { gamesMap[it.tournament] = it.points }
            return PlayerTournamentAggregation(pseudo, gamesMap)
        } catch (t: Throwable) {
            throw Exception(t.message, t)
        }
    }

    fun getAllPlayers(): List<RankedPlayerDTO> {
        val players = ArrayList<RankedPlayerDTO>()
        gameRepository.getAll().stream()
            .collect(Collectors.groupingBy(Game::pseudo))
            .values.stream().forEach { players.add(enrichPlayerWithTournamentRanking(aggregatePlayerByTournament(it))) }
        return players
    }

    private fun aggregatePlayerByTournament(playerGames: List<Game>): PlayerTournamentAggregation {
        val mapTournament = playerGames.stream()
            .filter { !it.tournament.isNullOrEmpty() }
            .collect(Collectors.groupingBy(Game::tournament, summingInt(Game::points)))
        if (mapTournament.isNotEmpty())
            return PlayerTournamentAggregation(playerGames[0].pseudo, mapTournament)
        else
            throw Exception("no player found for this pseudo")
    }

    fun deleteUser(pseudo: String): Boolean {
        return gameRepository.deleteAllGameByPseudo(pseudo)
    }

}