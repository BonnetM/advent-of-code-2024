package org.example.day12

class AreaAndSides {
    private data class Point(val x: Int, val y: Int)
    private sealed class Side {
        data class Left(val x: Int, val y: Int) : Side()
        data class Right(val x: Int, val y: Int) : Side()
        data class Top(val x: Int, val y: Int) : Side()
        data class Bottom(val x: Int, val y: Int) : Side()
    }

    fun compute(filename: String) {
        val map = readFile(filename)

        val regions = findRegions(map)
        val res = regions.sumOf {
            val area = it.size
            val sides = computeSides(it)
            area * sides
        }
        println("res: $res")

    }

    private fun computeSides(region: Set<Point>): Int {
        val allSides = region.flatMap { point ->
            listOfNeighbors(point)
                .filter { !region.contains(it) }
                .map {
                    if (it.x < point.x) {
                        Side.Left(point.x, point.y)
                    } else if (it.x > point.x) {
                        Side.Right(point.x, point.y)
                    } else if (it.y < point.y) {
                        Side.Top(point.x, point.y)
                    } else {
                        Side.Bottom(point.x, point.y)
                    }
                }
        }
        var sum = 0
        var currentX = -1
        var currentY = -1
        allSides
            .filterIsInstance<Side.Left>()
            .sortedWith(compareBy({ it.x }, { it.y }))
            .forEach { side ->
                if (currentX != side.x) {
                    sum++
                    currentX = side.x
                    currentY = side.y
                } else {
                    if (currentY != side.y - 1) {
                        sum++
                    }
                    currentY = side.y
                }
            }

        currentX = -1
        currentY = -1

        allSides
            .filterIsInstance<Side.Right>()
            .sortedWith(compareBy({ it.x }, { it.y }))
            .forEach { side ->
                if (currentX != side.x) {
                    sum++
                    currentX = side.x
                    currentY = side.y
                } else {
                    if (currentY != side.y - 1) {
                        sum++
                    }
                    currentY = side.y
                }
            }

        currentX = -1
        currentY = -1

        allSides
            .filterIsInstance<Side.Top>()
            .sortedWith(compareBy({ it.y }, { it.x }))
            .forEach { side ->
                if (currentY != side.y) {
                    sum++
                    currentY = side.y
                    currentX = side.x
                } else {
                    if (currentX != side.x - 1) {
                        sum++
                    }
                    currentX = side.x
                }
            }


        currentX = -1
        currentY = -1

        allSides
            .filterIsInstance<Side.Bottom>()
            .sortedWith(compareBy({ it.y }, { it.x }))
            .forEach { side ->
                if (currentY != side.y) {
                    sum++
                    currentY = side.y
                    currentX = side.x
                } else {
                    if (currentX != side.x - 1) {
                        sum++
                    }
                    currentX = side.x
                }
            }

        return sum
    }


    private fun listOfNeighbors(point: Point): List<Point> {
        val neighbors = mutableListOf<Point>()
        neighbors.add(Point(point.x + 1, point.y))
        neighbors.add(Point(point.x - 1, point.y))
        neighbors.add(Point(point.x, point.y + 1))
        neighbors.add(Point(point.x, point.y - 1))
        return neighbors
    }


    private fun findRegions(map: List<List<Char>>): MutableSet<MutableSet<Point>> {
        val visitedPoints = mutableSetOf<Point>()
        val regions = mutableSetOf<MutableSet<Point>>()

        map.indices.forEach { i ->
            map[i].indices.forEach { j ->
                if (!visitedPoints.contains(Point(i, j))) {
                    val regionChar = map[i][j]
                    val regionSet = mutableSetOf<Point>()
                    addContiguousPointsToRegion(map, i, j, regionChar, regionSet)
                    visitedPoints.addAll(regionSet)
                    regions.add(regionSet)
                }
            }
        }

        return regions
    }

    private fun addContiguousPointsToRegion(
        map: List<List<Char>>,
        i: Int,
        j: Int,
        regionChar: Char,
        regionSet: MutableSet<Point>,
    ) {
        try {
            if (regionChar == map[i][j] && !regionSet.contains(Point(i, j))) {
                regionSet.add(Point(i, j))
                addContiguousPointsToRegion(map, i + 1, j, regionChar, regionSet)
                addContiguousPointsToRegion(map, i - 1, j, regionChar, regionSet)
                addContiguousPointsToRegion(map, i, j + 1, regionChar, regionSet)
                addContiguousPointsToRegion(map, i, j - 1, regionChar, regionSet)
            }
        } catch (e: Exception) {
            // out of bonds, it's ok
        }
    }

    private fun readFile(filename: String): List<List<Char>> {
        return this::class.java.getResourceAsStream(filename)!!.bufferedReader()
            .readLines().map { line ->
                line.toCharArray().toList()
            }
    }
}