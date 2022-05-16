package fr.isen.deluc.tierchaser.adapter

import android.media.Image
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.isen.deluc.tierchaser.FilActuActivity
import fr.isen.deluc.tierchaser.ObjectModel
import fr.isen.deluc.tierchaser.R

class FilActuAdapter (
    private val context: FilActuActivity,
    private val filActuList: List<ObjectModel>,
    private val layoutId: Int
    ) : RecyclerView.Adapter<FilActuAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val objectImage = view.findViewById<ImageView>(R.id.image_item)
        val pseudo_utilisateur:TextView? = view.findViewById<TextView>(R.id.pseudo_utilisateur)
        val starIcon = view.findViewById<ImageView>(R.id.star_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentObject = filActuList[position]

        Glide.with(context).load(Uri.parse(currentObject.imageUrl)).into(holder.objectImage)

        holder.pseudo_utilisateur?.text = currentObject.user


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