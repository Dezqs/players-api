package fr.betclic.domain.game

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class Game(
    @BsonId
    val id: Id<Game>? = null,
    val pseudo: String,
    val tournament: String,
    val points: Int
)
