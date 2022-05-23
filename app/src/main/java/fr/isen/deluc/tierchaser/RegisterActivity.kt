package fr.isen.deluc.tierchaser

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import fr.isen.deluc.tierchaser.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {
    private  lateinit var database: DatabaseReference
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.validateBtn.setOnClickListener {

            /*FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result
                // Log and toast


                Toast.makeText(baseContext, "msg", Toast.LENGTH_SHORT).show()
            })*/

            when {
                TextUtils.isEmpty(binding.email.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(this, "Please enter an email", Toast.LENGTH_LONG).show()
                }
                TextUtils.isEmpty(binding.password.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(this, "Please enter a password", Toast.LENGTH_LONG).show()
                }



                else -> {
                    val email: String = binding.email.text.toString().trim() { it <= ' ' }
                    val password: String = binding.password.text.toString().trim() { it <= ' ' }
                    val userName: String = binding.userName.text.toString().trim() { it <= ' ' }
                    //val token:String = token

                    auth = FirebaseAuth.getInstance()
                    val userID = auth.currentUser?.uid

                    database = FirebaseDatabase.getInstance().getReference("Users")
                    val user = User(userName, email, password)
                    if(userID != null){
                        database.child(userID).setValue(user).addOnSuccessListener {
                            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                        }
                            .addOnFailureListener {
                                Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
                            }
                    }



                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> { task ->
                                if (task.isSuccessful) {
                                    val user: FirebaseUser = task.result!!.user!!
                                    val intent = Intent(this, VerifyActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id", userName)
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    Toast.makeText(
                                        this,
                                        "You are registered successfully",
                                        Toast.LENGTH_LONG
                                    ).show()
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