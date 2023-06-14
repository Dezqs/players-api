package fr.betclic.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.adminRoutes(){
    routing {
        route("/admin"){
            ping()
        }
    }
}

fun Route.ping(){
    get("/ping"){
        call.respondText("ALL_OK")
    }
}