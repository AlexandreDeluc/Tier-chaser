package fr.isen.deluc.tierchaser

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.isen.deluc.tierchaser.ObjectRepository.Singleton.databaseRef
import fr.isen.deluc.tierchaser.ObjectRepository.Singleton.objectList

class ProfileRepository {


    object Singleton {

        //se connecter à la référence "Objects"
        val databaseRef = FirebaseDatabase.getInstance().getReference("Objects")

        //créer une liste qui va contenir nos objets
        val objectList = arrayListOf<ObjectModel>()


    }

    fun updateData(callback: () -> Unit){
        //absorber les données depuis la databaseRef -> liste d'objets
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //retirer les anciens
                objectList.clear()

                //recolter la liste
                for (ds in snapshot.children) {
                    //construire un objet
                    val objet = ds.getValue(ObjectModel::class.java)
                    val liked = ds.child("liked").getValue(ObjectModel::class.java) as Boolean

                    //vérifier que l'item n'est pas null
                    if (objet != null) {
                        //ajouter l'objet à notre liste
                        objectList.add(objet)
                    }
                }
                //actionner le callback
                callback()
            }
            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    //mettre à jour un objet en BDD
    fun updateObject(objet: ObjectModel){
        databaseRef.child(objet.id).setValue(objet)
    }
}