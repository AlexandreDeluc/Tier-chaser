package fr.isen.deluc.tierchaser

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fr.isen.deluc.tierchaser.databinding.ActivityRegisterBinding
import kotlinx.android.synthetic.main.activity_login.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)


        binding.validateBtn.setOnClickListener {

            when {
                TextUtils.isEmpty(binding.email.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(this, "Please enter an email", Toast.LENGTH_LONG).show()
                }
                TextUtils.isEmpty(binding.password.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(this, "Please enter a password", Toast.LENGTH_LONG).show()
                }

                else -> {
                    val email: String = binding.email.text.toString().trim() { it <= ' '}
                    val password: String = binding.password.text.toString().trim() { it <= ' ' }
                    val userName: String = binding.userName.text.toString().trim() { it <= ' '}

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> { task ->
                                if (task.isSuccessful) {
                                    val user: FirebaseUser = task.result!!.user!!

                                    //Toast.makeText(this, "You are registered successfully", Toast.LENGTH_LONG).show()

                                    val intent = Intent(this, VerifyActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id", userName)
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        )
                }
            }

        }
        binding.alreadyBtn.setOnClickListener {
            val intent =  Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}