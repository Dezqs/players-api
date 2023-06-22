package fr.betclic.services

import com.mongodb.client.MongoClient
import fr.betclic.domain.game.GameRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TournamentService : KoinComponent {

    private val client: MongoClient by inject()
    private val gameRepository: GameRepository = GameRepository(client)


    fun deleteTournament(tournamentName: String): Boolean {
        return gameRepository.deleteTournament(tournamentName)
    }

}