package fr.isen.deluc.tierchaser.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.isen.deluc.tierchaser.ObjectModel
import fr.isen.deluc.tierchaser.R
import fr.isen.deluc.tierchaser.ShowObjectActivity

class ObjectAdapter(
    private val context: ShowObjectActivity,
    private val objectList: List<ObjectModel>,
    private val layoutId: Int
    ) : RecyclerView.Adapter<ObjectAdapter.ViewHolder>() {

    //boite pour ranger tout les composants à controler
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val objectImage = view.findViewById<ImageView>(R.id.image_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //recuperer les infos de l'objet
        val currentObject = objectList[position]

        //utiliser glide pour récuperer l'image à partir de son lien -> composant
        Glide.with(context).load(Uri.parse(currentObject.imageUrl)).into(holder.objectImage)


    }

    override fun getItemCount(): Int {
        return objectList.size
    }
}