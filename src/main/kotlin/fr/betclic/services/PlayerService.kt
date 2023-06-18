package fr.betclic.services

import com.mongodb.client.MongoClient
import fr.betclic.domain.player.Player
import fr.betclic.domain.player.PlayerRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PlayerService : KoinComponent {

    private val client: MongoClient by inject()
    private val playerRepository: PlayerRepository = PlayerRepository(client)

    fun getPlayerByPseudo(pseudo: String): Player {
        return playerRepository.findUser("pseudo") ?: error("Can't find $pseudo user")
    }

    fun getAllPlayers(): List<Player> {
        return playerRepository.getAll()
    }

    fun deleteUser(pseudo: String): Boolean {
        return playerRepository.deleteByPseudo(pseudo)
    }

    //TODO : improve process to avoid duplication ??? Or everything (new game) will be a "player"
    fun addPlayer(player: Player) : Player{
        return playerRepository.add(player)
    }

}