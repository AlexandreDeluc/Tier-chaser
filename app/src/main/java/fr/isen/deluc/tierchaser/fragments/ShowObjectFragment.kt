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