package fr.betclic.services

import com.mongodb.client.MongoClient
import fr.betclic.domain.player.Player
import fr.betclic.domain.player.PlayerRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PlayerService : KoinComponent {

    private val client: MongoClient by inject()
    private val playerRepository: PlayerRepository = PlayerRepository(client)

    fun getPlayerByPseudo(pseudo: String) : Player{
        return playerRepository.findUser("pseudo") ?: error("Can't find $pseudo user")
    }

}