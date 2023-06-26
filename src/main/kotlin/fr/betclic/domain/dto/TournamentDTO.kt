package fr.betclic.domain.dto

import kotlinx.serialization.Serializable

@Serializable
data class TournamentDTO (
    val tournamentName: String,
    val pseudo : String,
    val pointsInTournament: Int
)