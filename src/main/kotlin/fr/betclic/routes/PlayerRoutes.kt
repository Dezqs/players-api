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

            get("/{pseudo}"){
                call.respond(playerService.getPlayerByPseudo(call.parameters["pseudo"].orEmpty()))
            }

        }
    }
}
