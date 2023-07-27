package uz.ipoteka.samplefullproject.utils.state

import uz.ipoteka.samplefullproject.utils.core.NetworkError

sealed class UIState<T> {
    class Loading<T> : UIState<T>()
    class Error<G>(val error: NetworkError) : UIState<G>()
    class Success<T>(val data: T) : UIState<T>()
}