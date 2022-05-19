package fr.isen.deluc.tierchaser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.isen.deluc.tierchaser.ObjectModel
import fr.isen.deluc.tierchaser.ObjectRepository.Singleton.objectList
import fr.isen.deluc.tierchaser.ProfileActivity
import fr.isen.deluc.tierchaser.R
import fr.isen.deluc.tierchaser.adapter.ProfileAdapter

class ProfileFragment (
    private val context: ProfileActivity
        ) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_profile, container, false)


        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = ProfileAdapter(context, objectList, R.layout.item_vertical_profile)

        return view

    }
}