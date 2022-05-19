package fr.isen.deluc.tierchaser

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import fr.isen.deluc.tierchaser.databinding.ActivityEditProfilBinding

class EditProfilActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditProfilBinding
    lateinit var imageUri: Uri
    lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.profilPhoto.setOnClickListener {
            pickImage()
        }

        binding.saveBtn.setOnClickListener {
            saveImage()
        }
        binding.backToProfil.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==100 && resultCode== RESULT_OK){
            imageUri=data?.data!!
            binding.profilPhoto.setImageURI(imageUri)
        }
    }

    private fun saveImage() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading file ...")
        progressDialog.setCancelable(false)
        progressDialog.show()


        val ref = FirebaseStorage.getInstance().getReference("images/")
        ref.putFile(imageUri)
            .addOnSuccessListener {
                binding.profilPhoto.setImageURI(imageUri)
                Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
                progressDialog.dismiss()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show()
                progressDialog.dismiss()
            }
    }

    private fun pickImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 100)
    }
}