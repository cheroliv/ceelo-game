package game.ceelo

import game.ceelo.CeeloDicesHandDomain.is123
import game.ceelo.CeeloDicesHandDomain.is456
import game.ceelo.CeeloDicesHandDomain.isStraight
import game.ceelo.CeeloDicesHandDomain.isUniformDoublet
import game.ceelo.CeeloDicesHandDomain.isUniformTriplet
import game.ceelo.CeeloDicesHandDomain.uniformDoubletValue
import game.ceelo.CeeloDicesHandDomain.uniformTripletValue
import game.ceelo.DiceRunResult.*


object CeeloGameDomain {
    /**
     * un jet de dés au hazard
     */
    fun runDices(): List<Int> = List(size = 3, init = { (ONE..SIX).random() })

    fun randomNumberOfPlayers(): Int {
        return (TWO..SIX).random()
    }

    fun initBank(howMuchPlayer: Int): List<Int> = List(
        size = howMuchPlayer,
        init = { (ONE..SIX).random() }
    )

    fun List<List<Int>>.second(): List<Int> = if (isEmpty())
        throw NoSuchElementException("second player throw is empty.")
    else elementAt(index = 1)


    fun List<List<Int>>.third(): List<Int> = if (isEmpty())
        throw NoSuchElementException("third player throw is empty.")
    else elementAt(index = 2)

    fun List<List<Int>>.fourth(): List<Int> = if (isEmpty())
        throw NoSuchElementException("fourth player throw is empty.")
    else elementAt(index = 3)

    fun List<List<Int>>.fifth(): List<Int> = if (isEmpty())
        throw NoSuchElementException("fourth player throw is empty.")
    else elementAt(index = 4)

    fun List<List<Int>>.sixth(): List<Int> = if (isEmpty())
        throw NoSuchElementException("fourth player throw is empty.")
    else elementAt(index = 5)

    fun List<List<Int>>.compareRuns(): List<Int> {
        var winner = first()
        forEachIndexed { index: Int, hand ->
            if (index >= ONE) {
                val result = hand.compareThrows(this[index - 1])
                if (result == WIN) {
                    winner = hand
                }
            }
        }
        return winner
    }

    @Suppress("MemberVisibilityCanBePrivate")
    val List<Int>.whichCase: Int
        get() = when {
            is456 -> AUTOMATIC_WIN_456_CASE
            is123 -> AUTOMATIC_LOOSE_123_CASE
            isStraight -> STRAIGHT_234_345_CASE
            isUniformTriplet -> UNIFORM_TRIPLET_CASE
            isUniformDoublet -> UNIFORM_DOUBLET_CASE
            else -> OTHER_THROW_CASE
        }

    /**
     * compare un jet à un autre
     * pour renvoyer un resultat de jeu
     */
    fun List<Int>.compareThrows(secondPlayerThrow: List<Int>)
            : DiceRunResult =
        whichCase.run whichCase@{
            @Suppress("IMPLICIT_NOTHING_TYPE_ARGUMENT_AGAINST_NOT_NOTHING_EXPECTED_TYPE")
            secondPlayerThrow.whichCase.run otherWhichCase@{
                return when {
                    this@whichCase > this@otherWhichCase -> WIN
                    this@whichCase < this@otherWhichCase -> LOOSE
                    else -> onSameCase(
                        secondPlayerThrow = secondPlayerThrow,
                        throwCase = this@whichCase
                    )
                }
            }
        }

    @Suppress("MemberVisibilityCanBePrivate")
    fun List<Int>.onSameCase(
        secondPlayerThrow: List<Int>,
        throwCase: Int
    ): DiceRunResult = when (throwCase) {
        AUTOMATIC_WIN_456_CASE -> RETHROW
        AUTOMATIC_LOOSE_123_CASE -> RETHROW
        STRAIGHT_234_345_CASE -> when {
            containsAll(`2_3_4`) && secondPlayerThrow.containsAll(`2_3_4`) -> RETHROW
            containsAll(`3_4_5`) && secondPlayerThrow.containsAll(`3_4_5`) -> RETHROW
            containsAll(`2_3_4`) && secondPlayerThrow.containsAll(`3_4_5`) -> LOOSE
            else -> WIN
        }
        UNIFORM_TRIPLET_CASE -> when {
            uniformTripletValue > secondPlayerThrow.uniformTripletValue -> WIN
            uniformTripletValue < secondPlayerThrow.uniformTripletValue -> LOOSE
            else -> RETHROW
        }
        UNIFORM_DOUBLET_CASE -> when {
            uniformDoubletValue > secondPlayerThrow.uniformDoubletValue -> WIN
            uniformDoubletValue < secondPlayerThrow.uniformDoubletValue -> LOOSE
            else -> RETHROW
        }
        else -> when {
            sum() > secondPlayerThrow.sum() -> WIN
            sum() < secondPlayerThrow.sum() -> LOOSE
            else -> RETHROW
        }
    }
}