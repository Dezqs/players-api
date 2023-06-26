package fr.betclic.routes

import fr.betclic.domain.dto.ErrorResponse
import fr.betclic.domain.dto.GameDTO
import fr.betclic.services.GameService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.gameRoutes(){

    val gameService : GameService by inject()

    routing {
        route("/game"){
            post {
                 try {
                    val gameDTO = call.receive<GameDTO>()
                    gameService.addGame(gameDTO).let {
                        call.respond(HttpStatusCode.Created,"Game stored correctly")
                        }
                } catch (t: Throwable) {
                     call.respond(HttpStatusCode.InternalServerError, ErrorResponse.SERVER_ERROR_RESPONSE)
                }
            }

            get("/all") {
                return@get try {
                    call.respond(gameService.getAll())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.NoContent, ErrorResponse.GAME_NOT_FOUND_RESPONSE)
                }
            }
        }
    }
}