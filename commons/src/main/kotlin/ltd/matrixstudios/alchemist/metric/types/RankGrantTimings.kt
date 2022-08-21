package ltd.matrixstudios.alchemist.metric.types

import ltd.matrixstudios.alchemist.metric.Metric

class RankGrantTimings : Metric() {

    var last10Entries = mutableListOf<Long>()


    override fun addEntry(value: Long) {
        last10Entries.add(value)
    }

    override fun fetch(): Long {
        var totalValue = 0L

        last10Entries.forEach {
            totalValue += it
        }

        if (last10Entries.size == 0) return 0L

        return totalValue / last10Entries.size
    }
}