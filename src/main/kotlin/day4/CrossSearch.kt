package org.example.day4

class CrossSearch {

    fun compute(filename: String) {
        val array = readFile(filename)
        var sum = 0
        for (i in array.indices) {
            for (j in array[i].indices) {
                sum += isOk(array, i, j)
            }
        }
        println("$sum")
    }

    private fun isOk(input: List<List<Char>>, i: Int, j: Int): Int {
        return try {

            if (input[i][j] == 'A'
                && (
                        (input[i + 1][j - 1] == 'M' && input[i - 1][j + 1] == 'S')
                                || (input[i + 1][j - 1] == 'S' && input[i - 1][j + 1] == 'M')
                        )
                && (
                        (input[i - 1][j - 1] == 'M' && input[i + 1][j + 1] == 'S')
                                || (input[i - 1][j - 1] == 'S' && input[i + 1][j + 1] == 'M')
                        )
            ) {
                1
            } else {
                0
            }
        } catch (e: Exception) {
            0
        }
    }

    private fun readFile(filename: String): List<List<Char>> {
        return this::class.java.getResourceAsStream(filename)!!.bufferedReader()
            .readLines()
            .map { it.toList() }
    }

}