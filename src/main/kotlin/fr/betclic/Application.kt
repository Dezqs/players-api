package fr.betclic

import fr.betclic.domain.player.PlayerRepository
import fr.betclic.domain.player.PlayerRepositoryImpl
import fr.betclic.routes.adminRoutes
import fr.betclic.plugins.configureSerialization
import fr.betclic.routes.playerRoutes
import fr.betclic.services.PlayerService
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.ktor.plugin.KoinApplicationStarted
import org.koin.ktor.plugin.KoinApplicationStopPreparing
import org.koin.ktor.plugin.KoinApplicationStopped
import org.koin.logger.slf4jLogger

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

fun Application.main() {
    install(Koin) {

        environment.monitor.subscribe(KoinApplicationStarted) {
            log.info("Koin started.")
        }

        environment.monitor.subscribe(KoinApplicationStopPreparing) {
            log.info("Koin stopping...")
        }

        environment.monitor.subscribe(KoinApplicationStopped) {
            log.info("Koin stopped.")
        }

        slf4jLogger()
        configureSerialization()

        adminRoutes()
        playerRoutes()

        modules(appModule)

    }
}

val appModule = module {
    single<PlayerRepository> { PlayerRepositoryImpl() }
    single { PlayerService(get()) }
}
