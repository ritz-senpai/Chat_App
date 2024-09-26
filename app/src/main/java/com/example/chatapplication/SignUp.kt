package com.example.chatapplication

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    private lateinit var edtName : EditText
    private lateinit var edtEmail : EditText
    private lateinit var edtPassword : EditText
    private lateinit var btnSignUp : Button
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mdbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#9370DB")))

        mAuth = FirebaseAuth.getInstance()
        edtName = findViewById(R.id.edt_name)
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnSignUp = findViewById(R.id.btnSignUp)

       btnSignUp.setOnClickListener {
           val name = edtName.text.toString()
           val email = edtEmail.text.toString()
           val password = edtPassword.text.toString()
           signUp(name,email , password)
       }
    }//end of onCreate()

    private fun signUp(name:String,email:String , password: String){
        //sign up logic
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //code to jump to home activity
                    //also add the user data to database
                    addUserToDatabase(name,email, mAuth.currentUser?.uid!!)

                    val intent = Intent(this@SignUp , MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                         Toast.makeText(this@SignUp,"some error occurred",Toast.LENGTH_SHORT).show()
                }
            }

    }//end of signUp()

    private fun addUserToDatabase(name:String,email:String,uid:String){
        mdbRef = FirebaseDatabase.getInstance().getReference()
        mdbRef.child("user").child(uid).setValue(User(name,email,uid))

    }
}