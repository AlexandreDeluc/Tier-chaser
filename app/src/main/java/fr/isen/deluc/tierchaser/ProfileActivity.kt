package fr.isen.deluc.tierchaser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.deluc.tierchaser.fragments.ProfileFragment

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container3, ProfileFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }
}