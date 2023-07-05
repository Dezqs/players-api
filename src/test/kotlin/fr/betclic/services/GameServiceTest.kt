package fr.betclic.services

import fr.betclic.domain.dto.GameDTO
import fr.betclic.domain.dto.toGame
import fr.betclic.domain.dto.toGameDTO
import fr.betclic.domain.game.Game
import fr.betclic.domain.game.GameRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.dsl.module
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class GameServiceTest : BaseServiceTest() {

    @MockK
    private val gameRepository: GameRepository = mockk()

    private lateinit var service: GameService

    init {
        startInjection(
            module {
                single { service }
            }
        )
    }

    @BeforeEach
    fun setUp() {
        clearMocks(gameRepository)
        service = GameService(gameRepository)
    }

    @Test
    fun test_getAllWithoutData() {
        every { gameRepository.getAll() } returns emptyList()
        val result = service.getAll()
        verify { gameRepository.getAll() }
        assertTrue(result.isEmpty())
    }

    @Test
    fun test_getAll() {
        val game = Game(null, "pseudo", "tournament", 0)
        every { gameRepository.getAll() } returns listOf(game)

        val result = service.getAll()
        verify { gameRepository.getAll() }
        assertFalse(result.isEmpty())
        assertEquals(game.toGameDTO(), result[0])
    }

    @Test
    fun test_addGame() {
        val gameDTO = GameDTO("pseudo", "tournament", 0)
        val game = Game(null, "pseudo2", "tournament2", 2)


        val slot = slot<Game>()
        every { gameRepository.add(capture(slot)) } returns game

        val result = service.addGame(gameDTO)
        assertEquals(gameDTO.toGame(), slot.captured)
        assertEquals(game.toGameDTO(), result)
    }

    @Test
    fun test_addGame_Exception() {
        val gameDTO = GameDTO("pseudo", "tournament", 0)

        val slot = slot<Game>()
        every { gameRepository.add(capture(slot)) } throws Exception("An Exception")

        assertThrows<Exception> { service.addGame(gameDTO) }
    }
}