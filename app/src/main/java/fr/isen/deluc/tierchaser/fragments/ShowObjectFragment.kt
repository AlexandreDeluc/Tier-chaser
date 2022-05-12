package fr.isen.deluc.tierchaser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.isen.deluc.tierchaser.ObjectModel
import fr.isen.deluc.tierchaser.R
import fr.isen.deluc.tierchaser.ShowObjectActivity
import fr.isen.deluc.tierchaser.adapter.ObjectAdapter
import fr.isen.deluc.tierchaser.adapter.ObjectItemDecoration

class ShowObjectFragment(
    private val context: ShowObjectActivity
        ) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_show_object, container, false)

        //créer une liste qui stocke les objets
        val objectList = arrayListOf<ObjectModel>()

        //enregistrer un premier objet dans notre liste (vinyle)
        objectList.add(ObjectModel(
            "vinyle",
            "l'auteur est Patrick Bruel",
                "https://cdn.pixabay.com/photo/2017/11/06/15/52/blank-vinyl-record-jacket-2924018_960_720.jpg",
            "50 euros",
            false
        ))

        //enregistrer un second objet dans notre liste (vinyle)
        objectList.add(ObjectModel(
                "voiture",
                "xxxxxxx",
                "https://cdn.pixabay.com/photo/2016/02/13/13/11/oldtimer-1197800_960_720.jpg",
                "50 000 euros",
                false
        ))

        //enregistrer un troisieme objet dans notre liste (vinyle)
        objectList.add(ObjectModel(
            "chaussures",
            "xxxxxxx",
            "https://cdn.pixabay.com/photo/2016/11/19/18/06/feet-1840619_960_720.jpg",
            "120 euros",
            false
        ))

        //enregistrer un quatrieme objet dans notre liste (vinyle)
        objectList.add(ObjectModel(
            "téléphone",
            "xxxxxxx",
            "https://cdn.pixabay.com/photo/2014/08/05/10/30/iphone-410324_960_720.jpg",
            "500 euros",
            false
        ))



        //recuperer le recycler view
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizontalRecyclerView.adapter = ObjectAdapter(context, objectList, R.layout.item_horizontal_object)

        //recuperer le second recycler view

        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = ObjectAdapter(context, objectList, R.layout.item_vertical_object)
        verticalRecyclerView.addItemDecoration(ObjectItemDecoration())

        return view
    }
}