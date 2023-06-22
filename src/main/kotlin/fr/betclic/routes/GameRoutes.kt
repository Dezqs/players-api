package fr.betclic.routes

import fr.betclic.domain.dto.GameDTO
import fr.betclic.domain.dto.toGame
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
                return@post try {
                    val gameDTO = call.receive<GameDTO>()
                    gameService.addGame(gameDTO)
                    call.respondText("Game stored correctly", status = HttpStatusCode.Created)
                } catch (t: Throwable) {
                    throw t
                }
            }

            get("/all") {
                call.respond(gameService.getAll())
            }
        }
    }
}