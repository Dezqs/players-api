package fr.betclic.services

import fr.betclic.domain.player.Player
import fr.betclic.domain.player.PlayerRepository

class PlayerService(private val playerRepository: PlayerRepository) {

    fun getDefaultPlayer() : Player = playerRepository.findUser("toto") ?: error("Can't find default user")

}