package fr.betclic.routes

import fr.betclic.services.PlayerService
import fr.betclic.services.TournamentService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.adminRoutes(){

    val playerService : PlayerService by inject()
    val tournamentService : TournamentService by inject()

    routing {
        route("/admin"){

            get("/ping"){
                call.respondText("ALL_OK")
            }

            delete("/player/{pseudo}"){
                try {
                    if(playerService.deleteUser(call.parameters["pseudo"].orEmpty())){
                        call.respond(HttpStatusCode.Accepted)
                    }else
                        call.respond(HttpStatusCode.NoContent)
                }catch (e: Exception){
                    throw e
                }
            }

            delete("/tournament/{name}"){
                try {
                    if(tournamentService.deleteTournament(call.parameters["name"].orEmpty())){
                        call.respond(HttpStatusCode.Accepted)
                    }else
                        call.respond(HttpStatusCode.NoContent)
                }catch (e: Exception){
                    throw e
                }
            }
        }
    }
}