package uz.ipoteka.samplefullproject.utils.core

import okhttp3.ResponseBody


/**
 * Wrapper class for network errors
 *
 * @author Shahzod
 */
sealed class NetworkError {
    /**
     * State for unexpected exceptions, for example «HTTP code - 500» or exceptions when mapping models
     */
    class Unexpected(val error: String) : NetworkError()

    /**
     * State for default errors from server size
     */
    class Api(val error: ResponseBody?, val errorCode:Int) : NetworkError()

    /**
     * State for displaying errors in input fields
     *
     * @param error
     * Map [Key][kotlin.collections.Map.Entry.key] is input name,
     * Map [Value][kotlin.collections.Map.Entry.value] is errors from server side
     */
    class ApiInputs(val error: ResponseBody?,val errorCode:Int) : NetworkError()

    /**
     * State for Timeout exceptions
     */
    object Timeout : NetworkError()

    class SocketTimeOutException(val error: String): NetworkError()

    object NoInternet : NetworkError()

    class IOException(val error: String): NetworkError()
}