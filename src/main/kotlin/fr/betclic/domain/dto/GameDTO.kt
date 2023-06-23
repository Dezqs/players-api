package fr.betclic.domain.dto

import fr.betclic.domain.game.Game
import kotlinx.serialization.Serializable

@Serializable
data class GameDTO(
    val playerPseudo: String,
    val tournament: String?,
    val points: Int
)


fun GameDTO.toGame(): Game = Game(
    pseudo = this.playerPseudo,
    tournament = this.tournament,
    points = this.points
)

fun Game.toGameDTO(): GameDTO = GameDTO(
    playerPseudo = this.pseudo,
    tournament = this.tournament,
    points = this.points
)
