package fr.isen.deluc.tierchaser

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import fr.isen.deluc.tierchaser.adapter.ProfileAdapter
import fr.isen.deluc.tierchaser.databinding.ActivityProfileBinding
import java.io.File

class ProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityProfileBinding
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        val repo = ProfileRepository()

        val storageRef = FirebaseStorage.getInstance().getReference("images/")
        val localfile = File.createTempFile("tempImage", "png")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding.leftPicture.setImageBitmap(bitmap)
        }
            .addOnFailureListener{
                Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show()
            }

        binding.editBtn.setOnClickListener {
            val intent = Intent(this, EditProfilActivity::class.java)
            startActivity(intent)
            finish()
        }

        repo.updateData {

            val verticalRecyclerView = findViewById<RecyclerView>(R.id.vertical_recycler_view)
            verticalRecyclerView.adapter = ProfileAdapter(this, repo.objectList, R.layout.item_vertical_profile)

        }

    }
}