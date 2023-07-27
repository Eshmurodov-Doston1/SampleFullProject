package uz.ipoteka.samplefullproject.screens.container

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.activity.viewModels
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import uz.ipoteka.samplefullproject.R
import uz.ipoteka.samplefullproject.databinding.ActivityMainBinding
import uz.ipoteka.samplefullproject.models.post.PostModel
import uz.ipoteka.samplefullproject.utils.extensions.collectUIState
import uz.ipoteka.samplefullproject.vm.MainViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel:MainViewModel by viewModels()
    private val binding:ActivityMainBinding by viewBinding()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        postView()
    }

    private fun postView():Unit = with(binding) {
        viewModel.postList()
        viewModel.postList.collectUIState(this@MainActivity,
            state = {

            },
            onSuccess = {
                val gson = Gson()
                val fromJson:List<PostModel> = gson.fromJson(it.toString())
                var items = ""
                fromJson.forEach { listItem->
                   items += listItem.toString()
                }
                txt.text = "$items \n"
                        },
            onError = {

            }
        )
    }
}

internal inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)