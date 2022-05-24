package fr.isen.deluc.tierchaser.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.isen.deluc.tierchaser.ObjectModel
import fr.isen.deluc.tierchaser.R
import fr.isen.deluc.tierchaser.databinding.FilActuVerticalBinding

class FilActuAdapter(
    private val context: Context,
    private val filActuList: List<ObjectModel>
) : RecyclerView.Adapter<FilActuAdapter.ViewHolder>() {

    inner class ViewHolder(binding: FilActuVerticalBinding) : RecyclerView.ViewHolder(binding.root) {
        val objectImage = binding.imageItem
        val pseudo_utilisateur = binding.pseudoUtilisateur
        val starIcon = binding.starIcon
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FilActuVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentObject = filActuList[position]

        Glide.with(context).load(Uri.parse(currentObject.imageUrl)).into(holder.objectImage)

        holder.pseudo_utilisateur.text = currentObject.user


        if(currentObject.liked) {
            holder.starIcon.setImageResource(R.drawable.ic_like)
        }
        else {
            holder.starIcon.setImageResource((R.drawable.ic_unlike))
        }
    }

    override fun getItemCount(): Int {
        return filActuList.size
    }
}