package fr.betclic.domain.dto

import kotlinx.serialization.Serializable

@Serializable
data class TournamentDTO (
    val tournamentName: String,
    val pointsInTournament: Int,
    val ranking: Int
)