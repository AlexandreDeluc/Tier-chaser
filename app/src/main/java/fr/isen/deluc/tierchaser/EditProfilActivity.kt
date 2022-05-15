package fr.isen.deluc.tierchaser

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.deluc.tierchaser.databinding.ActivityEditProfilBinding

class EditProfilActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditProfilBinding
    lateinit var ImageUri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.profilPhoto.setOnClickListener {
            selectImage()
        }
        binding.saveBtn.setOnClickListener {

        }
        binding.backToProfil.setOnClickListener {
            val intent = Intent(this, ProfilActivity::class.java)
            startActivity(intent)
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type="image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==100&&resultCode== RESULT_OK){
            ImageUri=data?.data!!
            binding.profilPhoto.setImageURI(ImageUri)
        }
    }

}