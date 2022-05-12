package fr.isen.deluc.tierchaser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.isen.deluc.tierchaser.databinding.ActivityProfilBinding
import java.lang.Exception
import java.lang.System.load

class ProfilActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfilBinding
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        loadUserInfo()

        binding.editBtn.setOnClickListener {
            val intent = Intent(this, EditProfilActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun loadUserInfo() {
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseAuth.uid!!)
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val username = "${snapshot.child("name").value}"
                    val profilPicture = "${snapshot.child("imProfile").value}"

                    binding.name.text = username

                    try {
                        Glide.with(this@ProfilActivity)
                            .load(profilPicture)
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(binding.letftPicture)
                    }
                    catch (e:Exception){

                    }

                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }
}
