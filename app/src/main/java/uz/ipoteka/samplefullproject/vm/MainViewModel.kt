package uz.ipoteka.samplefullproject.vm

import androidx.lifecycle.ViewModel
import com.google.gson.JsonElement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import uz.ipoteka.samplefullproject.repoContainer.repository.ApiRepository
import uz.ipoteka.samplefullproject.utils.constant.AppConstant.EMPTY_MAP
import uz.ipoteka.samplefullproject.utils.extensions.MutableUIStateFlow
import uz.ipoteka.samplefullproject.utils.extensions.collectNetworkRequest
import uz.ipoteka.samplefullproject.utils.networkHelper.NetworkHelper
import uz.ipoteka.samplefullproject.utils.state.UIState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val apiRepository: ApiRepository
):ViewModel() {
    val postList:StateFlow<UIState<JsonElement>> get() = _postList
    private val _postList = MutableUIStateFlow<JsonElement>()

    fun postList() = apiRepository.methodGet("/posts",EMPTY_MAP)
        .collectNetworkRequest(_postList,this,networkHelper) {
            it
        }
}