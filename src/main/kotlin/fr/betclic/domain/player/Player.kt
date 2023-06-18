package fr.betclic.domain.player

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id

@Serializable
data class Player(
    @Contextual val id: Id<Player>,
    val pseudo: String,
    val tournamentName: String,
    val points: Int
)
