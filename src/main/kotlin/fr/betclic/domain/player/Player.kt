package fr.betclic.domain.player

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val pseudo: String,
    val tournament: String,
    val points: Int
)
