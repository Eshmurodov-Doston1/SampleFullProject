package uz.ipoteka.samplefullproject.screens.home

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import uz.ipoteka.samplefullproject.R
import uz.ipoteka.samplefullproject.databinding.HomeScreenBinding
import uz.ipoteka.samplefullproject.models.post.PostModel
import uz.ipoteka.samplefullproject.screens.container.fromJson
import uz.ipoteka.samplefullproject.screens.home.adapter.PostAdapter
import uz.ipoteka.samplefullproject.utils.extensions.collectUIState
import uz.ipoteka.samplefullproject.vm.MainViewModel

@AndroidEntryPoint
class HomeScreen : Fragment(R.layout.home_screen) {
    private val binding:HomeScreenBinding by viewBinding()
    private val viewModel:MainViewModel by viewModels()
    private val postAdapter:PostAdapter by lazy { PostAdapter() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postView()
    }

    private fun postView():Unit = with(binding) {
        viewModel.postList()
        viewModel.postList.collectUIState(viewLifecycleOwner,
            state = {

            },
            onSuccess = {
                val gson = Gson()
                val fromJson:List<PostModel> = gson.fromJson(it.toString())
                postAdapter.submitList(fromJson)
                rvItem.adapter = postAdapter
            },
            onError = {

            }
        )
    }
}