package fr.betclic.domain.dto

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(val message: String) {
    companion object {
        val PLAYER_NOT_FOUND_RESPONSE = ErrorResponse("Player was not found")
        val TOURNAMENT_NOT_FOUND_RESPONSE = ErrorResponse("Tournament was not found")
        val GAME_NOT_FOUND_RESPONSE = ErrorResponse("Game was not found")
        val SERVER_ERROR_RESPONSE = ErrorResponse("Oups, something went wrong while processing your request...")
    }
}
