package fr.betclic.domain.player

interface PlayerRepository {
    fun findUser(pseudo: String): Player?
    fun addUser(player: Player)
}