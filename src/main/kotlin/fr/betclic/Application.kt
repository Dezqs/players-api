package fr.betclic

import fr.betclic.domain.dbModule
import fr.betclic.plugins.configureSerialization
import fr.betclic.routes.adminRoutes
import fr.betclic.routes.playerRoutes
import fr.betclic.services.PlayerService
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.main() {
    install(Koin) {

        slf4jLogger()

        configureSerialization()

        adminRoutes()
        playerRoutes()

        modules(dbModule,services)

    }
}

val services = module {
    single { PlayerService() }
}