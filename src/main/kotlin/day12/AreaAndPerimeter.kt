package org.example.day12

class AreaAndPerimeter {
    private data class Point(val x: Int, val y: Int)

    fun compute(filename: String) {
        val map = readFile(filename)

        val regions = findRegions(map)
        val res = regions.sumOf {
            val area = it.size
            val perimeter = computePerimeter(it)
            area * perimeter
        }
        println(res)

    }

    private fun computePerimeter(region: Set<Point>): Int {
        return region.sumOf {
            4 - listOfNeighbors(it)
                .filter { region.contains(it) }
                .size
        }
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