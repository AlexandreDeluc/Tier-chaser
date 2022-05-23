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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fr.isen.deluc.tierchaser.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("476295566771-bai0rnvvv813ivgd44nt97lvgtbm9h9u.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.googleBtn.setOnClickListener {
            signInWithGoogle()
        }

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
            finish()
        }

        binding.forgotPassword.setOnClickListener {
            showDialog()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException){

            }
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
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Email sent", Toast.LENGTH_LONG).show()
                    }
                }

        })
        builder.setNegativeButton(
            "Cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }

    private fun signIn() {
        val email: String = binding.email.text.toString().trim() { it <= ' ' }
        val password: String = binding.password.text.toString().trim() { it <= ' ' }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                val firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

                    if (task.isSuccessful) {

                        if (firebaseUser?.isEmailVerified!!) {

                            val intent = Intent(this, HomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                            intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                            intent.putExtra("email_id", email)

                            Toast.makeText(this, "Account is verified, you are logged in", Toast.LENGTH_LONG).show()
                            finish()
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this, "Account not verified", Toast.LENGTH_LONG).show()
                        }
                    }
                     else {
                        Toast.makeText(this, task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }
    private fun signInWithGoogle(){
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if(user != null) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        const val RC_SIGN_IN = 1001
    }
}

