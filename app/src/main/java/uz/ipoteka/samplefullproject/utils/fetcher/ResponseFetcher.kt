package uz.ipoteka.samplefullproject.utils.fetcher

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import timber.log.Timber
import uz.ipoteka.samplefullproject.utils.core.Either
import uz.ipoteka.samplefullproject.utils.core.NetworkError
import uz.ipoteka.samplefullproject.utils.dataMapper.DataMapper
import java.io.IOException
import java.io.InterruptedIOException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResponseFetcher @Inject constructor()  {
    fun <T : S, S> doNetworkRequestWithMapping(
        request: suspend () -> Response<T>
    ): Flow<Either<NetworkError, S>> = doNetworkRequest(request) { body ->
        Either.Right(body)
    }

    private fun <T, S> doNetworkRequest(
        request: suspend () -> Response<T>,
        successful: (T) -> Either.Right<S>
    ) = flow {
        request().let {
            when {
                it.isSuccessful && it.body() != null -> {
                    emit(successful.invoke(it.body()!!))
                }
                !it.isSuccessful && it.code() == 422 -> {
                    emit(Either.Left(NetworkError.ApiInputs(it.errorBody(),it.code())))
                }
                else -> {
                    emit(Either.Left(NetworkError.Api(it.errorBody(),it.code())))
                }
            }
        }
    }.flowOn(Dispatchers.IO).catch { exception ->
        when (exception) {
            is InterruptedIOException -> {
                emit(Either.Left(NetworkError.Timeout))
            }
            is TimeoutException ->{
                emit(Either.Left(NetworkError.Timeout))
            }
            is SocketTimeoutException ->{
                emit(Either.Left(NetworkError.SocketTimeOutException(exception.localizedMessage?.toString() ?: "")))
            }
            is IOException->{
                emit(Either.Left(NetworkError.IOException(exception.localizedMessage?.toString() ?: "")))
            }
            else -> {
                val message = exception.localizedMessage ?: "Error Occurred!"
                Timber.tag(this.javaClass.simpleName).d(message)
                emit(Either.Left(NetworkError.Unexpected(message)))
            }
        }
    }

}