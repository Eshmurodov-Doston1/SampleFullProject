package uz.ipoteka.samplefullproject.repoContainer.repositoryImpl

import com.google.gson.JsonElement
import uz.ipoteka.samplefullproject.repoContainer.repository.ApiRepository
import uz.ipoteka.samplefullproject.service.ApiService
import uz.ipoteka.samplefullproject.utils.core.RemoteWrapper
import uz.ipoteka.samplefullproject.utils.fetcher.ResponseFetcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val responseFetcher: ResponseFetcher
): ApiRepository {

    override fun  methodGet(
        path: String,
        queryMap: HashMap<String, String>?
    ): RemoteWrapper<JsonElement>  = responseFetcher.doNetworkRequestWithMapping {
        apiService.methodeGet(url = path, queryMap = queryMap)
    }

    override fun  methodPost(
        path: String,
        data: Any,
        queryMap: HashMap<String, String>?
    ): RemoteWrapper<JsonElement> = responseFetcher.doNetworkRequestWithMapping {
        apiService.methodePost(url = path, body = data,queryMap = queryMap)
    }

    override fun  methodPut(
        path: String,
        data: Any,
        queryMap: HashMap<String, String>?
    ): RemoteWrapper<JsonElement> = responseFetcher.doNetworkRequestWithMapping {
        apiService.methodePut(url = path, body = data,queryMap = queryMap)
    }

    override fun  methodeDelete(
        path: String,
        data: Any,
        queryMap: HashMap<String, String>?
    ): RemoteWrapper<JsonElement> = responseFetcher.doNetworkRequestWithMapping {
        apiService.methodeDelete(url = path, body = data,queryMap = queryMap)
    }
}