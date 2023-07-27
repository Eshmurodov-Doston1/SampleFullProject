package uz.ipoteka.samplefullproject.repoContainer.repository

import com.google.gson.JsonElement
import uz.ipoteka.samplefullproject.utils.core.RemoteWrapper

interface ApiRepository {
    // TODO: Methode Repository Get
    fun methodGet(path:String, queryMap:HashMap<String,String>?): RemoteWrapper<JsonElement>

    // TODO: Methode Repository Post
    fun methodPost(path:String, data:Any, queryMap:HashMap<String,String>?): RemoteWrapper<JsonElement>

    // TODO: Methode Repository Put
    fun methodPut(path:String, data:Any, queryMap:HashMap<String,String>?): RemoteWrapper<JsonElement>

    // TODO: Methode Repository Delete
    fun methodeDelete(path:String, data:Any,queryMap:HashMap<String,String>?): RemoteWrapper<JsonElement>
}