package fr.betclic.domain.player

import fr.betclic.domain.tournament.Tournament
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.litote.kmongo.*

@Serializable
data class Player(
    @Contextual val id: Id<Player>,
    val pseudo: String,
    val tournament: Tournament,
    val points: Int
)
