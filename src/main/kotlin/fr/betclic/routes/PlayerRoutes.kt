package fr.betclic.routes

import fr.betclic.services.PlayerService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.playerRoutes(){
    routing {

        val playerService : PlayerService by inject()

        route("/players"){
            get{
                call.respondText("Return sorted collection of players")
            }

            get("/{id}"){
                val playerId = call.parameters["id"]
                call.respond(playerService.getDefaultPlayer().pseudo)
            }

        }
    }
}
