package fr.isen.deluc.tierchaser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import fr.isen.deluc.tierchaser.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.photoBtn -> {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, 10)
                    true
                }
                else -> false
            }
        }
    }
}






