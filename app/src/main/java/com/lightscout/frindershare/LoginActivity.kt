package com.lightscout.frindershare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.atomic.AtomicBoolean

class LoginActivity : AppCompatActivity() {

    private var signUpVisible = AtomicBoolean(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login_button.setOnClickListener {

            if(validLogin())
                loginUser()
        }

        su_button.setOnClickListener {
            if(validSignUp())
                signUpNewUser()
        }


        signUp_textView.setOnClickListener {
            if(signUpVisible.getAndSet(false)){
                val animation = AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right)
                signUp_cardView.startAnimation(animation)
                signUp_cardView.visibility = View.INVISIBLE
            } else{
                val animation = AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left)
                signUp_cardView.startAnimation(animation)
                signUp_cardView.visibility = View.VISIBLE
                signUpVisible.set(true)
            }
        }




    }






    private fun signUpNewUser() {
        val email = su_email_editText.text.toString().trim()
        val password = su_password_editText.text.toString().trim()

        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(this,"Sign up complete. Please verify email link", Toast.LENGTH_SHORT).show()
                    FirebaseAuth.getInstance().currentUser?.sendEmailVerification()

                    val animation = AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right)


                    signUp_cardView.startAnimation(animation)
                    signUp_cardView.visibility = View.INVISIBLE
                    signUpVisible.set(false)
                }else {
                    Toast.makeText(this,it.exception?.localizedMessage?: "oops!", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun validLogin(): Boolean {
            if(li_email_editText.text.isEmpty()){
                Toast.makeText(this,"Email cannot be empty!", Toast.LENGTH_SHORT).show()
                return false
            }else if(li_password_editText.text.isEmpty()){
                Toast.makeText(this,"Password cannot be empty", Toast.LENGTH_SHORT).show()
                return false
            }
            return true
    }

    private fun validSignUp(): Boolean {
        if(su_email_editText.text.isEmpty()){
            Toast.makeText(this,"Email cannot be empty!", Toast.LENGTH_SHORT).show()
            return false
        }else if(su_password_editText.text.isEmpty()){
            Toast.makeText(this,"Password cannot be empty", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }


    private fun loginUser() {
        val email = li_email_editText.text.toString().trim()
        val password = li_password_editText.text.toString().trim()

        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    if(FirebaseAuth.getInstance().currentUser?.isEmailVerified == true) {
                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, HomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)

                    }else
                        Toast.makeText(this,"Please verify you email",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,it.exception?.localizedMessage?: "oops!", Toast.LENGTH_SHORT).show()
                }
            }

    }
}