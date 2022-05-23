package fr.isen.deluc.tierchaser

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import fr.isen.deluc.tierchaser.databinding.ActivityProfilBinding
import kotlinx.android.synthetic.main.activity_profil.*
import java.io.File

class ProfilActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfilBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var usernameFB: String
    lateinit var bioFB: String
    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        database = Firebase.database(URL).reference

        binding.editBtn.setOnClickListener {
            val intent = Intent(this, EditProfilActivity::class.java)
            startActivity(intent)
        }

        retrieveDataUsername()
        retrieveDataBio()

        val storageRef = FirebaseStorage.getInstance().getReference("images/")
        val localfile = File.createTempFile("tempImage", "png")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding.letftPicture.setImageBitmap(bitmap)
        }
            .addOnFailureListener{
                Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show()
            }

    }
    private fun retrieveDataUsername() {
        database.child("Profil").child("username").get().addOnSuccessListener {
            if(it.exists()) {
                usernameFB = it.value.toString()
                binding.username.text = usernameFB.toString()
            }
        }
    }

    private fun retrieveDataBio() {
        database.child("Profil").child("bio").get().addOnSuccessListener {
            if (it.exists()) {
                bioFB = it.value.toString()
                binding.bio.text = bioFB.toString()
            }
        }
    }

    companion object {
        private const val URL = "https://tierchaser-default-rtdb.firebaseio.com/"
    }
}
