package fr.isen.deluc.tierchaser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.isen.deluc.tierchaser.databinding.ActivityHomeBinding
import fr.isen.deluc.tierchaser.fragments.FilActuFragment

class FilActuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fil_actu)

        //injecter le fragment dans notre boite (fragment_container2)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container2, FilActuFragment(this))
        transaction.addToBackStack(null)
        transaction.commit()

    }
}