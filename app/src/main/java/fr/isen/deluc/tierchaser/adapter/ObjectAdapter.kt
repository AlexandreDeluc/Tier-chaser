package fr.isen.deluc.tierchaser.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.isen.deluc.tierchaser.ObjectModel
import fr.isen.deluc.tierchaser.ObjectRepository
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
        val objectName:TextView? = view.findViewById(R.id.item_name)
        val objectDescription:TextView? = view.findViewById(R.id.item_description)
        val starIcon = view.findViewById<ImageView>(R.id.star_icon)
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

        //recuperer le repository
        val repo = ObjectRepository()

        //utiliser glide pour récuperer l'image à partir de son lien -> composant
        Glide.with(context).load(Uri.parse(currentObject.imageUrl)).into(holder.objectImage)

        //mettre à jour le nom de l'objet
        holder.objectName?.text = currentObject.name

        //mettre à jour la description de l'objet
        holder.objectDescription?.text = currentObject.description

        //verifier le like
        if(currentObject.liked){
            holder.starIcon.setImageResource(R.drawable.ic_like)
        }
        else{
            holder.starIcon.setImageResource(R.drawable.ic_unlike)
        }

        //rajouter une interaction sur l'étoile
        holder.starIcon.setOnClickListener {
            //inverse si le bouton est like ou non
            currentObject.liked = !currentObject.liked
            //mettre à jour l'objet
            repo.updateObject(currentObject)

        }
    }

    override fun getItemCount(): Int {
        return objectList.size
    }

}