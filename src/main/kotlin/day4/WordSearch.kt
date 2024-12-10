package org.example.day4

class WordSearch {

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
        return isHorizontalForwards(input, i, j) + isHorizontalBackwards(input, i, j) + isVerticalUpwards(
            input,
            i,
            j
        ) + isVerticalDownwards(input, i, j) + isDiagonal1(input, i, j) + isDiagonal2(input, i, j) + isDiagonal3(
            input,
            i,
            j
        ) + isDiagonal4(input, i, j)
    }

    private fun isHorizontalForwards(input: List<List<Char>>, i: Int, j: Int): Int {
        return try {
            if (input[i][j] == 'X' && input[i + 1][j] == 'M' && input[i + 2][j] == 'A' && input[i + 3][j] == 'S') 1 else 0
        } catch (e: Exception) {
            0
        }
    }

    private fun isHorizontalBackwards(input: List<List<Char>>, i: Int, j: Int): Int {
        return try {
            if (input[i][j] == 'X' && input[i - 1][j] == 'M' && input[i - 2][j] == 'A' && input[i - 3][j] == 'S') 1 else 0
        } catch (e: Exception) {
            0
        }
    }

    private fun isVerticalUpwards(input: List<List<Char>>, i: Int, j: Int): Int {
        return try {
            if (input[i][j] == 'X' && input[i][j - 1] == 'M' && input[i][j - 2] == 'A' && input[i][j - 3] == 'S') 1 else 0
        } catch (e: Exception) {
            0
        }
    }

    private fun isVerticalDownwards(input: List<List<Char>>, i: Int, j: Int): Int {
        return try {
            if (input[i][j] == 'X' && input[i][j + 1] == 'M' && input[i][j + 2] == 'A' && input[i][j + 3] == 'S') 1 else 0
        } catch (e: Exception) {
            0
        }
    }

    private fun isDiagonal1(input: List<List<Char>>, i: Int, j: Int): Int {
        return try {
            if (input[i][j] == 'X' && input[i - 1][j - 1] == 'M' && input[i - 2][j - 2] == 'A' && input[i - 3][j - 3] == 'S') 1 else 0
        } catch (e: Exception) {
            0
        }
    }

    private fun isDiagonal2(input: List<List<Char>>, i: Int, j: Int): Int {
        return try {
            if (input[i][j] == 'X' && input[i - 1][j + 1] == 'M' && input[i - 2][j + 2] == 'A' && input[i - 3][j + 3] == 'S') 1 else 0
        } catch (e: Exception) {
            0
        }
    }

    private fun isDiagonal3(input: List<List<Char>>, i: Int, j: Int): Int {
        return try {
            if (input[i][j] == 'X' && input[i + 1][j + 1] == 'M' && input[i + 2][j + 2] == 'A' && input[i + 3][j + 3] == 'S') 1 else 0
        } catch (e: Exception) {
            0
        }
    }

    private fun isDiagonal4(input: List<List<Char>>, i: Int, j: Int): Int {
        return try {
            if (input[i][j] == 'X' && input[i + 1][j - 1] == 'M' && input[i + 2][j - 2] == 'A' && input[i + 3][j - 3] == 'S') 1 else 0
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