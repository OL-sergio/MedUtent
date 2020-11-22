package projetoG5.ipca.medutent

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_registo.*

class ActivityRegisto : AppCompatActivity(){

    companion object {
        val TAG = "LoginActivity"
    }

    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registo)

        auth = Firebase.auth


        buttonRegistarUtilizador.setOnClickListener {
            auth.createUserWithEmailAndPassword(
                editTextNameRegisto.text.toString(),
                editTextPasswordRegisto.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        val intent = Intent(this, ActivityLogin::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()

                    }
                }
        }


        textViewRegistarReturnLogin.setOnClickListener {

            val intent = Intent (this,  ActivityLogin::class.java  )
            startActivity(intent)
        }

    }

}


