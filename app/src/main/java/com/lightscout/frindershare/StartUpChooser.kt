package com.lightscout.frindershare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth

class StartUpChooser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_start_up_chooser)

        var intent = if(FirebaseAuth.getInstance().currentUser?.isEmailVerified == true){
             Intent(this, HomeActivity::class.java)
        }else{
          Intent(this, LoginActivity::class.java)
        }

            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

    }
}