package fr.betclic.services

import com.mongodb.client.MongoClient
import fr.betclic.domain.game.Game
import fr.betclic.domain.game.GameRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class TournamentService : KoinComponent {

    private val client: MongoClient by inject()
    private val gameRepository: GameRepository = GameRepository(client)


    fun deleteTournament(tournamentName: String): Boolean {
        return gameRepository.deleteTournament(tournamentName)
    }

    fun updatePlayerPointsInTournament(pseudo: String, tournamentName: String, points : Int) : Boolean{
        if(gameRepository.findGamesByUser(pseudo).stream()
            .anyMatch{ it.tournament.equals(tournamentName, true)}){
            return Objects.nonNull(gameRepository.add(Game(null, pseudo, tournamentName, points)))
        }else
            throw Exception("This player doesn't participate to this tournament")
    }

}