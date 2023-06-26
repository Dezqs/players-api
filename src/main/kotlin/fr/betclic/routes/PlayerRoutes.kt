package fr.betclic.routes

import fr.betclic.domain.dto.ErrorResponse
import fr.betclic.domain.dto.PlayerDTO
import fr.betclic.services.PlayerService
import fr.betclic.services.TournamentService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.playerRoutes() {
    routing {

        val playerService: PlayerService by inject()
        val tournamentService: TournamentService by inject()

        route("/player") {
            get("/all") {
                return@get try {
                    call.respond(playerService.getAllPlayers())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.NoContent, ErrorResponse.PLAYER_NOT_FOUND_RESPONSE)
                }
            }

            get("/{pseudo}") {
                return@get try {
                    call.respond(playerService.getPlayerByPseudo(call.parameters["pseudo"].orEmpty()))
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.NoContent, ErrorResponse.PLAYER_NOT_FOUND_RESPONSE)
                }
            }

            post("/update") {
                return@post try {
                    val playerToUpdate = call.receive<PlayerDTO>()
                    if (tournamentService.updatePlayerPointsInTournament(
                            playerToUpdate.playerPseudo,
                            playerToUpdate.tournament,
                            playerToUpdate.points
                        )
                    ) {
                        call.respond(HttpStatusCode.Accepted)
                    } else
                        call.respond(HttpStatusCode.NoContent, "No user matches your criteria")
                } catch (t: Throwable) {
                    call.respond(HttpStatusCode.InternalServerError, ErrorResponse.SERVER_ERROR_RESPONSE)
                }
            }
        }

    }
}
