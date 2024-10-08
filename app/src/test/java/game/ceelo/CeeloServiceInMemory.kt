package game.ceelo

import game.ceelo.Game.runDices
import game.ceelo.CeeloServiceInMemory.InMemoryData.addGame
import game.ceelo.CeeloServiceInMemory.InMemoryData.getAllGames
import webapp.models.JwtToken
import webapp.models.ProblemsModel

class CeeloServiceInMemory : CeeloServiceKtor() {
    private object InMemoryData {
        private val repo: MutableList<List<List<Int>>> by lazy {
            MutableList(0) { mutableListOf(runDices(), runDices()) }
        }

        @JvmStatic
        fun getAllGames(): List<List<List<Int>>> = repo

        @JvmStatic
        fun addGame(game: List<List<Int>>) {
            repo.add(game)
        }
    }

    override fun allGames(): List<List<List<Int>>> = getAllGames()
    override fun saveGame(newGame: List<List<Int>>) = addGame(newGame)
    override suspend fun authenticate(login: String, password: String): Pair<ProblemsModel, JwtToken> {
        TODO("Not yet implemented")
    }
}