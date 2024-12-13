package org.example

import kotlinx.coroutines.runBlocking
import org.example.day11.BlinkingALot
import org.example.day12.AreaAndPerimeter
import org.example.day12.AreaAndSides
import org.example.day13.LargePositionTokenEquations
import org.example.day13.TokenEquations
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        val day = "13"
        val inputMode = true
        val filename = "/$day-${if (inputMode) "input" else "example"}.txt"
        println("Solving $filename")
        val time = measureTimeMillis {
            LargePositionTokenEquations().compute(filename)
        }
        println("Completed in $time ms")
    }
}