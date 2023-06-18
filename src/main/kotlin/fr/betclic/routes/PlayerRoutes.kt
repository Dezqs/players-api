package fr.betclic.routes

import fr.betclic.domain.player.Player
import fr.betclic.services.PlayerService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.playerRoutes() {
    routing {

        val playerService: PlayerService by inject()

        route("/player") {
            get("/all") {
                call.respond(playerService.getAllPlayers())
            }

            get("/{pseudo}") {

                return@get try {
                    call.respond(playerService.getPlayerByPseudo(call.parameters["pseudo"].orEmpty()))
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.NoContent, "No user matches your criteria")
                }
            }

            post {
                return@post try {
                    val player = call.receive<Player>()
                    playerService.addPlayer(player)
                    call.respondText("Player stored correctly", status = HttpStatusCode.Created)
                } catch (t: Throwable) {
                    throw t
                }
            }
        }

    }
}
