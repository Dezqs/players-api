package fr.betclic.domain.dto

import fr.betclic.domain.game.Game
import kotlinx.serialization.Serializable
import java.util.Collections

@Serializable
data class PlayerDTO(
    val pseudo: String,
    val games: Map<String, Int>
)

fun Game.toPlayerDto(): PlayerDTO = (
        PlayerDTO(
            pseudo = this.pseudo,
            games = Collections.emptyMap()
        )
        )