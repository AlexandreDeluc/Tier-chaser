package fr.isen.deluc.tierchaser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.isen.deluc.tierchaser.FilActuActivity
import fr.isen.deluc.tierchaser.ObjectModel
import fr.isen.deluc.tierchaser.R
import fr.isen.deluc.tierchaser.adapter.FilActuAdapter
import fr.isen.deluc.tierchaser.adapter.FilActuDecoration
import fr.isen.deluc.tierchaser.adapter.ObjectAdapter

class FilActuFragment (
        private val context: FilActuActivity
        ) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_fil_actu, container, false)

        val filActuList = arrayListOf<ObjectModel>()

        filActuList.add(
            ObjectModel(
            "xxx",
        "xxxx",
        "xxxx",
        "https://cdn.pixabay.com/photo/2022/05/12/19/11/flowers-7192179_960_720.jpg",
            "xxx",
            true,
                "c.nadaud"
        )
        )

        filActuList.add(
            ObjectModel(
                "xxx",
                "xxxx",
                "xxxx",
                "https://cdn.pixabay.com/photo/2014/04/03/10/52/winter-311566_960_720.png",
                "xxx",
                false,
                "c.nadaud"
            )
        )

        filActuList.add(
            ObjectModel(
                "xxx",
                "xxxx",
                "xxxx",
                "https://cdn.pixabay.com/photo/2022/04/18/13/27/yoga-7140566_960_720.jpg",
                "xxx",
                false,
                "c.nadaud"
            )
        )

        filActuList.add(
            ObjectModel(
                "xxx",
                "xxxx",
                "xxxx",
                "https://cdn.pixabay.com/photo/2022/05/07/20/22/flowers-7180947_960_720.jpg",
                "xxx",
                true,
                "c.nadaud"
            )
        )

        filActuList.add(
            ObjectModel(
                "xxx",
                "xxxx",
                "xxxx",
                "https://cdn.pixabay.com/photo/2022/05/12/19/11/flowers-7192179_960_720.jpg",
                "xxx",
                false,
                "c.nadaud"
            )
        )


        val filActuRecyclerView = view.findViewById<RecyclerView>(R.id.filactu_recycler_view)
        filActuRecyclerView.adapter = FilActuAdapter(context, filActuList, R.layout.fil_actu_vertical)
        filActuRecyclerView.addItemDecoration(FilActuDecoration())
        return view
    }
}