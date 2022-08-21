package ltd.matrixstudios.alchemist.metric

abstract class Metric {

    abstract fun addEntry(value: Long)

    abstract fun fetch() : Long
}