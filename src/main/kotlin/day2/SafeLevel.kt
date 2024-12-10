package org.example.day2

class SafeLevel {

    private data class Report(val levels: List<Int>)

    suspend fun compute(filename: String) {
        val reports = readFile(filename)

        val safe = reports.map { report ->
            val differences = (1..<report.levels.count()).map { i ->
                val firstLevel = report.levels[i - 1]
                val secondLevel = report.levels[i]
                val diff = firstLevel - secondLevel
                diff
            }

            if (differences.all { it in 1..3 } || differences.all { it in -1 downTo -3 }) {
                1
            } else {
                0
            }
        }.sum()

        println("$safe")
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