package ltd.matrixstudios.alchemist.metric.types

import ltd.matrixstudios.alchemist.metric.Metric

class ProfileTimings : Metric() {

    var last10Entries = mutableListOf<Long>()


    override fun addEntry(value: Long) {
        last10Entries.add(value)
    }

    override fun fetch(): Long {
       var totalValue = 0L

        last10Entries.forEach {
            totalValue += it
        }

        return totalValue / last10Entries.size
    }
}