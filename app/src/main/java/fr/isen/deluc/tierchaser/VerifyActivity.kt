package fr.isen.deluc.tierchaser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import fr.isen.deluc.tierchaser.databinding.ActivityVerifyBinding

class VerifyActivity : AppCompatActivity() {
    lateinit var binding: ActivityVerifyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.verifyBtn.setOnClickListener {
            sendEmailVerification()
        }
        binding.backBtn.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun sendEmailVerification() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.sendEmailVerification()?.addOnCompleteListener{
            Toast.makeText(this, "Email sent", Toast.LENGTH_LONG).show()
        }
    }
}