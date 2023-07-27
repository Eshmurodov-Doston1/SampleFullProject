package uz.ipoteka.samplefullproject.vm

import com.google.gson.JsonElement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import uz.ipoteka.samplefullproject.models.post.PostModel
import uz.ipoteka.samplefullproject.repoContainer.repository.ApiRepository
import uz.ipoteka.samplefullproject.utils.constant.AppConstant.EMPTY_MAP
import uz.ipoteka.samplefullproject.utils.networkHelper.NetworkHelper
import uz.ipoteka.samplefullproject.utils.state.UIState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val apiRepository: ApiRepository
):BaseViewModel(networkHelper) {
    val postList:StateFlow<UIState<JsonElement>> get() = _postList
    private val _postList = MutableUIStateFlow<JsonElement>()

    fun postList() = apiRepository.methodGet("/posts",EMPTY_MAP)
        .collectNetworkRequest(_postList) {
            it
        }
}