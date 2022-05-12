package fr.isen.deluc.tierchaser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.deluc.tierchaser.fragments.ShowObjectFragment

class ShowObjectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_object)

        //on charge notre ObjectRepository
        val repo = ObjectRepository()

        //mettre à jour la liste d'objets
        repo.updateData{
            //on injecte le fragment dans notre fragment_container
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, ShowObjectFragment(this))
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}