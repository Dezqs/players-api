package fr.betclic.routes

import fr.betclic.domain.dto.ErrorResponse
import fr.betclic.services.TournamentService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.tournamentRoutes() {

    val tournamentService: TournamentService by inject()

    routing {
        route("/tournament") {
            get("/{tournamentName}") {
                return@get try {
                    call.respond(
                        tournamentService
                            .getTournamentPlayersOrderedByPoints(call.parameters["tournamentName"].orEmpty())
                    )
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.NoContent, ErrorResponse.TOURNAMENT_NOT_FOUND_RESPONSE)
                }
            }
            get("/all"){
                return@get try {
                    call.respond(tournamentService.getAllTournament())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, ErrorResponse.SERVER_ERROR_RESPONSE)
                }
            }
        }
    }
}