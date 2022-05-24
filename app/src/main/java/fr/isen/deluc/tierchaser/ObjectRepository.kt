package fr.isen.deluc.tierchaser

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.isen.deluc.tierchaser.ObjectRepository.Singleton.databaseRef

class ObjectRepository {

    //créer une liste qui va contenir nos objets
    val objectList = arrayListOf<ObjectModel>()


    object Singleton {
        //se connecter à la référence "Objects"
        val databaseRef = FirebaseDatabase.getInstance().getReference("Objects")
    }

    fun updateData(callback: () -> Unit){
        //absorber les données depuis la databaseRef -> liste d'objets
        databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //retirer les anciens
                objectList.clear()

                //recolter la liste
                for (ds in snapshot.children){
                    //construire un objet
                    val objet = ds.getValue(ObjectModel::class.java)

                    //vérifier que la plante n'est pas null
                    if(objet != null){
                            objectList.add(objet)
                    }
                }
                //actionner le callback
                callback()
            }
            override fun onCancelled(error: DatabaseError) {}

        })
    }

    //mettre à jour un objet en BDD
    fun updateObject(objet: ObjectModel){
        databaseRef.child(objet.id).setValue(objet)
    }
}