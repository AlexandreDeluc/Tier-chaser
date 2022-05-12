package fr.isen.deluc.tierchaser

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import fr.isen.deluc.tierchaser.databinding.ActivityEditProfilBinding
import java.lang.Exception

class EditProfilActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditProfilBinding
    lateinit var firebaseAuth: FirebaseAuth
    private var imageUri: Uri?=null
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()
        loadUserInfo()

        binding.backToProfil.setOnClickListener {
            val intent = Intent(this, ProfilActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.saveBtn.setOnClickListener {
            validateData()
        }

        binding.profilPhoto.setOnClickListener {
            pickImageGallery()
        }
    }

        fun loadUserInfo() {
            val ref = FirebaseDatabase.getInstance().getReference("Users")
            ref.child(firebaseAuth.uid!!)
                .addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val username = "${snapshot.child("name").value}"
                        val profilPicture = "${snapshot.child("imProfile").value}"

                        binding.editUsername.setText(username)

                        try {
                            Glide.with(this@EditProfilActivity)
                                .load(profilPicture)
                                .placeholder(R.drawable.ic_launcher_background)
                                .into(binding.profilPhoto)
                        }
                        catch (e: Exception){

                        }

                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
        }

    private fun pickImageGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryActivityResultLauncher.launch(intent)
    }
    private var name = ""
    private fun validateData() {
        name = binding.editUsername.text.toString().trim()

        if(name.isEmpty()) {
            Toast.makeText(this, "Enter a name", Toast.LENGTH_LONG).show()
        }
        else {
            if(imageUri == null) {
                updateProfil("")
            }
            else {
                uploadImage()
            }
        }
    }

    private fun uploadImage() {
        progressDialog.setMessage("Uploading profile message")
        progressDialog.show()

        val filePathAndName = "ProfileImages/*"+firebaseAuth.uid
        val reference = FirebaseStorage.getInstance().getReference(filePathAndName)
        reference.putFile(imageUri!!)
            .addOnSuccessListener { taskSnapshot->
                val uriTask: Task<Uri> = taskSnapshot.storage.downloadUrl
                while (!uriTask.isSuccessful);
                val uploadedImageUrl = "${uriTask.result}"

                updateProfil(uploadedImageUrl)
            }
            .addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(this, "Failed to upload due to ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun updateProfil(uploadedImageUrl: String) {
        progressDialog.setMessage("Updating profile ...")

        val hashmap: HashMap<String, Any> = HashMap()
        hashmap["name"]= name
        if(imageUri != null){
            hashmap["imProfile"] = uploadedImageUrl
        }
        val reference = FirebaseDatabase.getInstance().getReference("Users")
        reference.child(firebaseAuth.uid!!)
            .updateChildren(hashmap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Profil updated", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(this, "Failed to update profile due to ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    private val galleryActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult> {result ->
            if(result.resultCode == Activity.RESULT_OK){
                val data = result.data
                imageUri = data!!.data

                binding.profilPhoto.setImageURI(imageUri)
            }
            else{
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            }
        }
    )
}