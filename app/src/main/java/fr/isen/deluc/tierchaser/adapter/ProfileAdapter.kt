package fr.isen.deluc.tierchaser.adapter

import android.net.Uri
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.isen.deluc.tierchaser.ObjectModel
import fr.isen.deluc.tierchaser.ProfileActivity
import fr.isen.deluc.tierchaser.ProfileRepository
import fr.isen.deluc.tierchaser.R

class ProfileAdapter (
    private val context: ProfileActivity,
    private val objectList: List<ObjectModel>,
    private val layoutId: Int
    ): RecyclerView.Adapter<ProfileAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val objectImage = view.findViewById<ImageView>(R.id.image_item)
        val starIcon = view.findViewById<ImageView>(R.id.star_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentObject = objectList[position]

        val repo = ProfileRepository()

        Glide.with(context).load(Uri.parse(currentObject.imageUrl)).into(holder.objectImage)

        if(currentObject.liked){
            holder.starIcon.setImageResource((R.drawable.ic_like))
        }
        else{
            holder.starIcon.setImageResource(R.drawable.ic_unlike)
        }

        holder.starIcon.setOnClickListener {
            currentObject.liked = !currentObject.liked

            repo.updateObject(currentObject)

        }

    }

    override fun getItemCount(): Int {
        return objectList.size
    }
}