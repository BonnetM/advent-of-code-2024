package org.example.day9

class DiskMemory {

    fun compute(filename: String) {
        val diskMap = readFile(filename)
        val explodedMemory = mutableListOf<Long?>()
        diskMap.forEachIndexed { i, char ->
            val count = char.digitToInt()
            if (i.mod(2) == 0) {
                // File
                repeat(count) {
                    explodedMemory.add(i.div(2L))
                }
            } else {
                // Free
                repeat(count) {
                    explodedMemory.add(null)
                }
            }
        }


        println(explodedMemory.joinToString(""))

        var indexToMove = explodedMemory.size - 1

        for (i in explodedMemory.indices) {
            if (i >= indexToMove) {
                break
            }
            while (explodedMemory[indexToMove] == null) {
                indexToMove--
            }
            if (explodedMemory[i] == null) {
                explodedMemory[i] = explodedMemory[indexToMove]
                explodedMemory[indexToMove] = null
                indexToMove--
            }
        }

        println(explodedMemory.joinToString(""))

        val res = explodedMemory.filterNotNull().mapIndexed { index, c ->
            index * c
        }.sum()

        println(res)
    }


    private fun readFile(filename: String): String {
        val lines = this::class.java.getResourceAsStream(filename)!!.bufferedReader()
            .readLines()
        return lines.first()
    }
}