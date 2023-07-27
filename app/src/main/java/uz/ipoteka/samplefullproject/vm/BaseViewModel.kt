package uz.ipoteka.samplefullproject.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import uz.ipoteka.samplefullproject.utils.core.Either
import uz.ipoteka.samplefullproject.utils.core.NetworkError
import uz.ipoteka.samplefullproject.utils.networkHelper.NetworkHelper
import uz.ipoteka.samplefullproject.utils.state.UIState

abstract class BaseViewModel(
    private val  networkHelper: NetworkHelper
    ) : ViewModel()
{

    /**
     * Creates a [MutableStateFlow] with [UIState] and the given initial value [UIState.Idle]
     */
    @Suppress("FunctionName")
    protected fun <T> MutableUIStateFlow() = MutableStateFlow<UIState<T>>(UIState.Loading())

    /**
     * Reset [MutableUIStateFlow] to [UIState.Idle]
     */
    protected fun <T> MutableStateFlow<UIState<T>>.reset() {
        value = UIState.Loading()
    }

    /**
     * Collect network request result without mapping for primitive types
     *
     * @receiver [collectUIState]
     */
    protected fun <T> Flow<Either<NetworkError, T>>.collectNetworkRequest(
        state: MutableStateFlow<UIState<T>>
    ) = collectUIState(state) {
        UIState.Success(it)
    }

    /**
     * Collect network request result with mapping
     *
     * @receiver [collectUIState]
     */
    protected fun <T, S> Flow<Either<NetworkError, T>>.collectNetworkRequest(
        state: MutableStateFlow<UIState<S>>,
        mapToUI: (T) -> S
    ) = collectUIState(state) {
        UIState.Success(mapToUI(it))
    }

    /**
     * Collect network request result and mapping [Either] to [UIState]
     *
     * @receiver [ErrorType<T] or [data][T] in [Flow] with [Either]
     *
     * @param T domain layer model
     * @param S presentation layer model
     * @param state [MutableStateFlow] with [UIState]
     *
     * @see viewModelScope
     * @see launch
     * @see [Flow.collect]
     */
    private fun <T, S> Flow<Either<NetworkError, T>>.collectUIState(
        state: MutableStateFlow<UIState<S>>,
        successful: (T) -> UIState.Success<S>
    ) {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                state.tryEmit(UIState.Loading())
                this@collectUIState.collectLatest {
                    when (it) {
                        is Either.Left -> state.tryEmit(UIState.Error(it.value))
                        is Either.Right -> state.tryEmit(successful(it.value))
                    }
                }
            } else {
                state.tryEmit(UIState.Error(NetworkError.NoInternet))
            }
        }
    }

    /**
     * Collect local request to database with mapping
     *
     * @receiver [T] with [Flow]
     *
     * @param T domain layer model
     * @param S presentation layer model
     * @param mapToUI high-order function for setup mapper functions
     */
    protected fun <T, S> Flow<T>.collectLocalRequest(
        mapToUI: (T) -> S
    ): Flow<S> = map { value: T ->
        mapToUI(value)
    }

    /**
     * Collect local request to database with mapping with [List]
     *
     * @receiver [T] in [List] with [Flow]
     *
     * @param T domain layer model
     * @param S presentation layer model
     * @param mapToUI high-order function for setup mapper functions
     */
    protected fun <T, S> Flow<List<T>>.collectLocalRequestForList(
        mapToUI: (T) -> S
    ): Flow<List<S>> = map { value: List<T> ->
        value.map { data: T ->
            mapToUI(data)
        }
    }

    /**
     * Collect paging request with mapping
     *
     * @receiver [PagingData] with [T] in [Flow]
     *
     * @param T domain layer model
     * @param S presentation layer model
     * @param mapToUI high-order function for setup mapper function
     *
     * @see cachedIn
     * @see viewModelScope
     */
//    protected fun <T : Any, S : Any> Flow<PagingData<T>>.collectPagingRequest(
//        mapToUI: (T) -> S
//    ): Flow<PagingData<S>> = map { value: PagingData<T> ->
//        value.map { data: T ->
//            mapToUI(data)
//        }
//    }.cachedIn(viewModelScope)
}