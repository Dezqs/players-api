package fr.betclic.domain.dto

import kotlinx.serialization.Serializable

@Serializable
data class InPlayerDTO(
    val playerPseudo: String,
    val tournament: String,
    val points: Int
)
