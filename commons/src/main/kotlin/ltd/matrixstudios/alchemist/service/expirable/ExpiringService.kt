package ltd.matrixstudios.alchemist.service.expirable

abstract class ExpiringService<T> {

    abstract fun getValues() : List<T>
    abstract fun save(element: T)

    abstract fun clearOutModels()


}