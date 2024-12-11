package org.example.day11

class BlinkingALot {

    private data class Input(val stone: Long, val depth: Int)

    private val cache = mutableMapOf<Input, Long>()

    suspend fun compute(filename: String) {
        val line = readFile(filename)
        val res = line.sumOf { stone ->
            expectedWidthFor(stone, blinksRemaining = 75)
        }
        println("res: $res")
    }

    private fun expectedWidthFor(stone: Long, blinksRemaining: Int): Long {
        return if (blinksRemaining == 0) {
            1
        } else if (cache[Input(stone, blinksRemaining)] != null) {
            cache[Input(stone, blinksRemaining)]!!
        } else {
            if (stone == 0L) {
                val value = expectedWidthFor(1, blinksRemaining - 1)
                cache[Input(stone, blinksRemaining)] = value
                value
            } else return if (stone.toString().length.mod(2) == 0) {
                val stoneString = stone.toString()
                val value1 = expectedWidthFor(
                    stoneString.substring(0, stoneString.length.div(2)).toLong(),
                    blinksRemaining - 1
                )
                val value2 = expectedWidthFor(
                    stoneString.substring(stoneString.length.div(2), stoneString.length).toLong(),
                    blinksRemaining - 1
                )
                cache[Input(stone, blinksRemaining)] = value1 + value2
                value1 + value2
            } else {
                val value = expectedWidthFor(stone * 2024L, blinksRemaining - 1)
                cache[Input(stone, blinksRemaining)] = value
                value
            }
        }
    }

    private fun readFile(filename: String): List<Long> {
        val lines = this::class.java.getResourceAsStream(filename)!!.bufferedReader()
            .readLines()

        return lines.first().split(" ").map { it.toLong() }

    }

}