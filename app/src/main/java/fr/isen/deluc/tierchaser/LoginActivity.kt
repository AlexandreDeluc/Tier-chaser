package fr.isen.deluc.tierchaser

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fr.isen.deluc.tierchaser.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            when {
                TextUtils.isEmpty(binding.email.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(this, "Please enter an email", Toast.LENGTH_LONG).show()
                }
                TextUtils.isEmpty(binding.password.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(this, "Please enter a password", Toast.LENGTH_LONG).show()
                }

                else -> {
                    signIn()
                }
            }
        }

        binding.noAccountBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.forgotPassword.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Reset your password")

        val input = EditText(this)
        input.setHint("Enter your mail address")
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            var emailInput = input.text.toString()
            Firebase.auth.sendPasswordResetEmail(emailInput)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Toast.makeText(this, "Email sent", Toast.LENGTH_LONG).show()
                    }
                }

        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }
    private fun signIn() {
        val email: String = binding.email.text.toString().trim() { it <= ' ' }
        val password: String = binding.password.text.toString().trim() { it <= ' ' }
        val firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val vEmail = firebaseUser?.isEmailVerified

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra(
                        "user_id",
                        FirebaseAuth.getInstance().currentUser!!.uid
                    )
                    intent.putExtra("email_id", email)
                    if(vEmail!!){
                        Toast.makeText(this, "Account is verified, you are logged in", Toast.LENGTH_LONG).show()
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this, "Account is not verified", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        task.exception!!.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }



    }
}