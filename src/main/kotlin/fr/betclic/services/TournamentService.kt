package fr.betclic.services

import com.mongodb.client.MongoClient
import fr.betclic.domain.dto.TournamentDTO
import fr.betclic.domain.dto.TournamentPlayerDTO
import fr.betclic.domain.game.Game
import fr.betclic.domain.game.GameRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*
import java.util.stream.Collectors

class TournamentService : KoinComponent {

    private val client: MongoClient by inject()
    private val gameRepository: GameRepository = GameRepository(client)


    fun deleteTournament(tournamentName: String): Boolean {
        return gameRepository.deleteTournament(tournamentName)
    }

    fun updatePlayerPointsInTournament(pseudo: String, tournamentName: String, points: Int): Boolean {
        if (gameRepository.findGamesByUser(pseudo).stream()
                .anyMatch { it.tournament.equals(tournamentName, true) }
        ) {
            return Objects.nonNull(gameRepository.add(Game(null, pseudo, tournamentName, points)))
        } else
            throw Exception("This player doesn't participate to this tournament")
    }

    fun getTournamentPlayersOrderedByPoints(tournamentName: String): TournamentDTO {

        val tournamentRanking = mutableListOf<TournamentPlayerDTO>()
        gameRepository.findGamesByTournament(tournamentName)
            .stream()
            .filter { it.tournament?.isNotBlank() ?: false }
            .collect(Collectors.groupingBy(Game::pseudo, Collectors.summingInt(Game::points)))
            .forEach { entry -> tournamentRanking.add(TournamentPlayerDTO(entry.key, 0, entry.value)) }

        tournamentRanking.sortByDescending { it.pointsInTournament }
        tournamentRanking.map { it.ranking = tournamentRanking.indexOf(it) + 1 }

        return TournamentDTO(tournamentName, tournamentRanking)
    }

    fun getAllTournament(): List<TournamentDTO> {

        val tournamentNames = gameRepository.getAll().stream()
            .filter { it.tournament?.isNotBlank() ?: false }
            .collect(Collectors.groupingBy(Game::tournament))
            .keys

        val tournaments = mutableListOf<TournamentDTO>()
        tournamentNames.forEach { if (it?.isNotBlank() == true)
            tournaments.add(getTournamentPlayersOrderedByPoints(it)) }

        return tournaments
    }

}