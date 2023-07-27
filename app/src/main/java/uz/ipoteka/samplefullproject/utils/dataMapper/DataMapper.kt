package uz.ipoteka.samplefullproject.utils.dataMapper

interface DataMapper<out T> {

    /**
     * Function for map data layer model to domain layer model
     */
    fun mapToDomain(): T
}