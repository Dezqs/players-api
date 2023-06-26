package fr.betclic.domain.dto

import kotlinx.serialization.Serializable

@Serializable
data class RankedPlayerDTO(
    val pseudo: String,
    val games: List<RankedTournamentDTO>
)
