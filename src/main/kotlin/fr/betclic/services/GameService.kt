package fr.betclic.services

import com.mongodb.client.MongoClient
import fr.betclic.domain.dto.GameDTO
import fr.betclic.domain.dto.toGame
import fr.betclic.domain.dto.toGameDTO
import fr.betclic.domain.game.Game
import fr.betclic.domain.game.GameRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GameService() : KoinComponent {

    private val client: MongoClient by inject()
    private var gameRepository: GameRepository = GameRepository(client)

    constructor(gameRepository: GameRepository) : this() {
        this.gameRepository = gameRepository
    }

    fun addGame(gameToAdd: GameDTO): GameDTO {
        return gameRepository.add(gameToAdd.toGame()).toGameDTO()
    }

    fun getAll(): List<GameDTO> {
        return gameRepository.getAll()
            .stream().map(Game::toGameDTO).toList()
    }

}