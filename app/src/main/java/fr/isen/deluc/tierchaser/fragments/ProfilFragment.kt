package fr.isen.deluc.tierchaser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.RecyclerView
import fr.isen.deluc.tierchaser.ProfilActivity
import fr.isen.deluc.tierchaser.R
import fr.isen.deluc.tierchaser.adapter.FilActuAdapter
import fr.isen.deluc.tierchaser.adapter.FilActuDecoration


class ProfilFragment (
        private val context: ProfilActivity
    ) : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val view = inflater?.inflate(R.layout.fragment_fil_actu, container, false)

            val filActuRecyclerView = view.findViewById<RecyclerView>(R.id.filactu_recycler_view)
            filActuRecyclerView.adapter = FilActuAdapter(context, filActuList, R.layout.fil_actu_vertical)
            filActuRecyclerView.addItemDecoration(FilActuDecoration())
            return view
        }
    }
}