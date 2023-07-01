package fr.betclic.domain.dto

import kotlinx.serialization.Serializable

@Serializable
data class TournamentDTO (
    val tournamentName: String,
    val players: List<TournamentPlayerDTO>
)

@Serializable
data class TournamentPlayerDTO(
    val pseudo: String,
    var ranking: Int,
    val pointsInTournament: Int
)