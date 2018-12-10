package day09

class Node(val value: Int) {

    var leftNeighbour: Node = this
    var rightNeighbour: Node = this

    fun insertAfter(node: Node) {
        rightNeighbour.leftNeighbour = node
        node.rightNeighbour = rightNeighbour

        rightNeighbour = node
        node.leftNeighbour = this
    }

    fun remove(): Node {
        rightNeighbour.leftNeighbour = leftNeighbour
        leftNeighbour.rightNeighbour = rightNeighbour
        return this
    }

}

class Game(private val players: Int, private val lastMarbleValue: Int) {

    private var currentMarbleValue = 0
    private var currentMarble: Node = Node(0)
    private var currentPlayer: Int = 0
    private val scores: MutableMap<Int, Long> = mutableMapOf()

    fun placeNextMarble(playerId: Int) {
        currentMarbleValue++

        if(currentMarbleValue % 23 == 0) {
            scores[playerId] = (scores[playerId] ?: 0) + currentMarbleValue

            //I'm so sorry...
            val seventhMarbleCCWRemoved = currentMarble
                    .leftNeighbour
                    .leftNeighbour
                    .leftNeighbour
                    .leftNeighbour
                    .leftNeighbour
                    .leftNeighbour
                    .leftNeighbour
                    .remove()
            scores[playerId] = (scores[playerId] ?: 0) + seventhMarbleCCWRemoved.value

            currentMarble = seventhMarbleCCWRemoved.rightNeighbour
        } else {
            val nextMarble = Node(currentMarbleValue)
            currentMarble.rightNeighbour.insertAfter(nextMarble)
            currentMarble = nextMarble
        }
    }

    fun play(): Map<Int, Long> {
        while (currentMarbleValue <= lastMarbleValue) {
            placeNextMarble(currentPlayer)

            currentPlayer++
            currentPlayer %= players
        }
        return scores
    }

}

fun main(vararg args: String) {

    // Input:
    // 473 players; last marble is worth 70904 points
    val players = 473
    val lastMarbleValue = 70904

    val scores = Game(players, lastMarbleValue).play()
    println("Highscore: ${scores.values.max()}")

    val scoresPart2 = Game(players, lastMarbleValue * 100).play()
    println("Highscore (100x): ${scoresPart2.values.max()}")

}
