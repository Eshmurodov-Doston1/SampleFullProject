package uz.ipoteka.samplefullproject.utils.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.ipoteka.samplefullproject.utils.core.NetworkError
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