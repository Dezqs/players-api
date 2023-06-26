package fr.betclic.routes

import fr.betclic.services.TournamentService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.tournamentRoutes(){

    val tournamentService : TournamentService by inject()

    routing {
        route("/tournament"){
            get("/{tournamentName}") {
                call.respond(tournamentService
                    .getTournamentPlayersOrderedByPoints(call.parameters["tournamentName"].orEmpty()))
            }
        }
    }
}