package org.example.day2

import kotlin.math.sign

class Dampener {

    private data class Report(val levels: List<Int>)

    suspend fun compute(filename: String) {
        val reports = readFile(filename)

        val safe = reports.map { report ->
            println("report: $report")
            val levels = report.levels
            val differences = computeDifferences(levels)

            if (isSafe(differences)) {
                println("safe !")
                1
            } else {
                var firstException = differences.indexOfFirst { it !in 1..3 && it !in -1 downTo -3 }
                if (firstException == -1) {
                    val firstSignum = differences.first().sign
                    firstException = differences.indexOfFirst { it.sign != firstSignum }
                }
                val firstTry = report.levels.toMutableList()
                firstTry.removeAt(firstException)
                val secondTry = report.levels.toMutableList()
                secondTry.removeAt(firstException + 1)
                val thirdTry = report.levels.toMutableList()
                thirdTry.removeAt(0)
                if (isSafe(computeDifferences(firstTry)) || isSafe(computeDifferences(secondTry) )|| isSafe(computeDifferences(thirdTry))) {
                    1
                } else {
                    0
                }
            }


        }.sum()

        println("$safe")

    }

    private fun isSafe(differences: List<Int>): Boolean {
        val b = differences.all { it in 1..3 } || differences.all { it in -1 downTo -3 }
        println("$differences --> $b")
        return b
    }

    private fun computeDifferences(levels: List<Int>) = (1..<levels.count()).map { i ->
        val firstLevel = levels[i - 1]
        val secondLevel = levels[i]
        val diff = firstLevel - secondLevel
        diff
    }

    private fun readFile(filename: String): List<Report> {
        return this::class.java.getResourceAsStream(filename)!!.bufferedReader()
            .readLines()
            .map {
                val splitted = it.split("\\s+".toRegex())
                Report(splitted.map { it.toInt() })
            }
    }
}