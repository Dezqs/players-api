package fr.betclic.domain.dto

import kotlinx.serialization.Serializable

@Serializable
data class PlayerTournamentAggregation(
    val pseudo: String,
    val games: Map<String?, Int>
)