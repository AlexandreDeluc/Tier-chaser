package fr.isen.deluc.tierchaser

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fr.isen.deluc.tierchaser.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val userId = intent.getStringExtra("user_id")

        //binding.emailId.text = "$userId"

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
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_logout, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val user = Firebase.auth.currentUser!!
        return when(item.itemId){
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
                finish()
                return true
            }
            R.id.deleteUser -> {
                user.delete()
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Account deleted", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, RegisterActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}