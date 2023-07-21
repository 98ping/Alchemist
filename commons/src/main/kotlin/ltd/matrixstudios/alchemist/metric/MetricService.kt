package ltd.matrixstudios.alchemist.metric

object MetricService {

    val metrics = hashMapOf<String, MutableList<Metric>>()

    fun getLast10(category: String): MutableList<Metric> {
        val forCategory = metrics.getOrDefault(category, mutableListOf())

        return forCategory.sortedBy { it.at }.take(10).toMutableList()
    }

    fun addMetric(service: String, metric: Metric) {
        metrics[service] = metrics.getOrDefault(service, mutableListOf()).apply { this.add(metric) }
    }


    fun averageMS(category: String) : Long {
        val found = metrics.getOrDefault(category, mutableListOf())

        if (found.size == 0) return Long.MAX_VALUE

        return (found.sumOf { it.ms }) / found.size
    }
}