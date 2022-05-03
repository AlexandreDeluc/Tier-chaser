package fr.isen.deluc.tierchaser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import fr.isen.deluc.tierchaser.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.photoBtn -> {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivity(intent)
                    true
                }

                R.id.filActuBtn -> {
                    val intent = Intent(this, ProductListActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}






