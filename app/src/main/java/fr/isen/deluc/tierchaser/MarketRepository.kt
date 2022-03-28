package fr.isen.deluc.tierchaser

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.isen.deluc.tierchaser.MarketRepository.Singleton.databaseRef
import fr.isen.deluc.tierchaser.MarketRepository.Singleton.marketList

class MarketRepository {

    //permet d'accèder aux deux valeurs sur toute l'app sans actualiser le contenu
    object Singleton {
        //se connecter à la référence "Markets"
        val databaseRef = FirebaseDatabase.getInstance().getReference("Markets")

        //pouvoir créer une liste qui contient nos markers
        val marketList = arrayListOf<MarketModel>()
    }

    //on demande les valeurs à Markets pour pouvoir les injecter dans la liste de Markets
    //avec une méthode permettant d'actualiser le contenu

    fun updateData(callback: () -> Unit) {
        //absorber les données depuis la databaseRef -> liste de markets
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //retirer les anciens market
                //recréer une liste neuve à chaque fois
                marketList.clear()
                //on récolte la liste
                for (ds in snapshot.children) { //on récupère les données enfant au sein de la base de données
                    //constuction d'un objet market par rapport aux données récoltées
                    val market = ds.getValue(MarketModel::class.java)
                    //verification que le market a bien été chargé (n'est pas nul)
                    if(market != null){
                        //ajouter le market à notre liste
                        marketList.add(market)
                    }
                }
                // actionner le callback
                callback()
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}