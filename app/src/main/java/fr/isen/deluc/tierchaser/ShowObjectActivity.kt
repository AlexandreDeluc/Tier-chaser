package fr.isen.deluc.tierchaser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import fr.isen.deluc.tierchaser.adapter.ObjectAdapter
import fr.isen.deluc.tierchaser.adapter.ObjectItemDecoration

class ShowObjectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_object)

        //on charge notre ObjectRepository
        val repo = ObjectRepository()

        //mettre à jour la liste d'objets
        repo.updateData{

            //recuperer le recycler view
            val horizontalRecyclerView = findViewById<RecyclerView>(R.id.horizontal_recycler_view)
            horizontalRecyclerView.adapter = ObjectAdapter(this, repo.objectList, R.layout.item_horizontal_object)

            //recuperer le second recycler view

            val verticalRecyclerView = findViewById<RecyclerView>(R.id.vertical_recycler_view)
            verticalRecyclerView.adapter = ObjectAdapter(this, repo.objectList, R.layout.item_vertical_object)
            verticalRecyclerView.addItemDecoration(ObjectItemDecoration())

        }
    }
}