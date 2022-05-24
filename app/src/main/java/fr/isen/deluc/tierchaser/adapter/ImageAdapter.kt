package fr.isen.deluc.tierchaser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.isen.deluc.tierchaser.databinding.ItemBinding

class ImageAdapter(private val imageList: ArrayList<String>) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // loading the images from the position
        Glide.with(holder.itemView.context).load(imageList[position]).into(holder.image)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}