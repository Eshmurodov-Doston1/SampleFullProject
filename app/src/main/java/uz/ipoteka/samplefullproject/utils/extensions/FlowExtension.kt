package uz.ipoteka.samplefullproject.utils.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.ipoteka.samplefullproject.utils.core.Either
import uz.ipoteka.samplefullproject.utils.core.NetworkError
import uz.ipoteka.samplefullproject.utils.networkHelper.NetworkHelper
import uz.ipoteka.samplefullproject.utils.state.UIState

fun <T> StateFlow<UIState<T>>.collectUIState(
    lifecycleOwner: LifecycleOwner,
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    state: ((UIState<T>) -> Unit)? = null,
    onError: ((error: NetworkError) -> Unit),
    onSuccess: ((data: T) -> Unit)
) {
    launchRepeatOnLifecycle(lifecycleOwner,lifecycleState) {
        this@collectUIState.collectLatest {
            when (it) {
                is UIState.Loading -> {
                    state?.invoke(it)
                }

                is UIState.Error -> {
                    onError.invoke(it.error)
                    this.cancel()
                }

                is UIState.Success -> {
                    onSuccess.invoke(it.data)
                    this.cancel()
                }
            }
        }

    }
}

private fun launchRepeatOnLifecycle(
    viewLifecycleOwner:LifecycleOwner,
    state: Lifecycle.State, block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(state) {
            block()
        }
    }
}

@Suppress("FunctionName")
fun <T> MutableUIStateFlow() = MutableStateFlow<UIState<T>>(UIState.Loading())

fun <T> MutableStateFlow<UIState<T>>.reset() {
    value = UIState.Loading()
}


fun <T, S> Flow<Either<NetworkError, T>>.collectNetworkRequest(
    state: MutableStateFlow<UIState<S>>,
    viewModel: ViewModel,
    networkHelper:NetworkHelper,
    mapToUI: (T) -> S
) = collectUIState(state,viewModel,networkHelper) {
    UIState.Success(mapToUI(it))
}

private fun <T, S> Flow<Either<NetworkError, T>>.collectUIState(
    state: MutableStateFlow<UIState<S>>,
    viewModel: ViewModel,
    networkHelper:NetworkHelper,
    successful: (T) -> UIState.Success<S>
) {
    viewModel.viewModelScope.launch {
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
