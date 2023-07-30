package uz.ipoteka.samplefullproject.screens.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.ipoteka.samplefullproject.databinding.ItemPostBinding
import uz.ipoteka.samplefullproject.models.post.PostModel

class PostAdapter:ListAdapter<PostModel,PostAdapter.Vh>(PostsDiffUtil()) {
    inner class Vh(private val binding:ItemPostBinding):RecyclerView.ViewHolder(binding.root) {
        fun onBind(data:PostModel):Unit = with(binding) {
            tv1.text = data.body
            tv1.text = data.body
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemPostBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position))
    }
}

class PostsDiffUtil():DiffUtil.ItemCallback<PostModel>(){
    override fun areItemsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
        return oldItem == newItem
    }

}