package uz.ipoteka.samplefullproject.screens.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.ipoteka.samplefullproject.utils.core.NetworkError
import uz.ipoteka.samplefullproject.utils.state.UIState

abstract class BaseScreen<Binding : ViewBinding, ViewModel : BaseViewModel>(@LayoutRes layoutId: Int)
    :Fragment(layoutId){
    protected abstract val binding: Binding
    protected abstract val viewModel: ViewModel

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        setupListeners()
    }

    protected open fun setupListeners() {}

    protected open fun initialize() {}

    protected fun <T> StateFlow<UIState<T>>.collectUIState(
        lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
        state: ((UIState<T>) -> Unit)? = null,
        onError: ((error: NetworkError) -> Unit),
        onSuccess: ((data: T) -> Unit)
    ) {
        launchRepeatOnLifecycle(lifecycleState) {
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
        state: Lifecycle.State, block: suspend CoroutineScope.() -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state) {
                block()
            }
        }
    }
}