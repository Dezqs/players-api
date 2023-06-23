package fr.betclic.domain.dto

import kotlinx.serialization.Serializable

@Serializable
data class OutPlayerDTO(
    val pseudo: String,
    val games: Map<String?, Int>
)