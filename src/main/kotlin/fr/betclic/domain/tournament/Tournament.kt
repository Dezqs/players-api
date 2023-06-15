package fr.betclic.domain.tournament

import fr.betclic.domain.player.Player
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id

@Serializable
data class Tournament(
    @Contextual val id: Id<Tournament>,
    val name: String = "",
    val players: List<Player> = emptyList()
)
