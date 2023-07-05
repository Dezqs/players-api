package fr.betclic.services

import fr.betclic.domain.dbModule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.test.KoinTest

abstract class BaseServiceTest : KoinTest {

    init {
        stopKoin()
    }

    fun startInjection(module: Module) {
        startKoin {
            modules(
                dbModule, module
            )
        }
    }
}