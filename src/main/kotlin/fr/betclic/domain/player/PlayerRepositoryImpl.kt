package fr.betclic.domain.player

class PlayerRepositoryImpl : PlayerRepository {

    override fun findUser(pseudo: String): Player? {
        return Player("toto");
    }

    override fun addUser(player: Player) {
        TODO("Not yet implemented")
    }
}