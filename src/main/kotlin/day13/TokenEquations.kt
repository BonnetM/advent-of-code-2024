package org.example.day13

class TokenEquations {

    private data class Button(val deltaX: Long, val deltaY: Long)
    private data class Point(val x: Long, val y: Long)
    private data class System(val buttonA: Button, val buttonB: Button, val prize: Point)

    fun compute(filename: String) {
        val systems = readFile(filename)
        val res = systems.sumOf {
            val r = solve(it)
            r
        }
        println("res = $res")

    }

    private fun solve(system: System): Long {
        with(system) {
            val deter = buttonA.deltaX * buttonB.deltaY - buttonA.deltaY * buttonB.deltaX
            if (deter == 0L) return 0L
            val numX = prize.x * buttonB.deltaY - prize.y * buttonB.deltaX
            val numY = prize.y * buttonA.deltaX - prize.x * buttonA.deltaY
            return if (numX.mod(deter) == 0L && numY.mod(deter) == 0L) {
                val x = (numX / deter)
                val y = numY / deter
                if (x > 100 || y > 100) {
                    throw IllegalStateException("> 100 for $this")
                } else {
                    3 * x + y
                }
            } else {
                0L
            }
        }
    }

    private fun readFile(filename: String): List<System> {
        return this::class.java.getResourceAsStream(filename)!!.bufferedReader()
            .readLines()
            .filter { it.isNotBlank() }
            .chunked(3) { lines ->

                System(
                    buttonA = parseButtonLine(lines[0]),
                    buttonB = parseButtonLine(lines[1]),
                    prize = parsePrize(lines[2])
                )
            }
    }

    private fun parseButtonLine(line: String): Button {
        val a = line.split("X+")[1]
        val x = a.split(",")[0].toLong()
        val y = a.split("Y+")[1].toLong()
        return Button(deltaX = x, deltaY = y)
    }

    private fun parsePrize(line: String): Point {
        val a = line.split("X=")[1]
        val x = a.split(",")[0].toLong()
        val y = a.split("Y=")[1].toLong()
        return Point(x, y)
    }
}