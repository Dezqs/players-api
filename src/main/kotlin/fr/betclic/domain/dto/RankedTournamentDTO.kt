package fr.betclic.domain.dto

import kotlinx.serialization.Serializable

@Serializable
data class RankedTournamentDTO(
    val tournament: String,
    val ranking: Int,
    val points: Int,
)