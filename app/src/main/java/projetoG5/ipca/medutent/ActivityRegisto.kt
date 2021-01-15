package projetoG5.ipca.medutent

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.common.collect.Iterables.isEmpty

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
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

            EmailRegisto.text.toString();
            PasswordRegisto.text.toString();
            PasswordRegistoConfirm.text.toString();

            if (TextUtils.isEmpty(EmailRegisto.toString())) {
                Toast.makeText(this, "Insira o email.", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(PasswordRegisto.toString())) {
                Toast.makeText(this, "Insira a password.", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(PasswordRegistoConfirm.toString())) {
                Toast.makeText(this, "Confirme a password.", Toast.LENGTH_SHORT).show();
            } else if (PasswordRegisto.equals(PasswordRegistoConfirm)) {
                Toast.makeText(this, "As passwords não são iguais", Toast.LENGTH_SHORT).show();
            } else {
                auth.createUserWithEmailAndPassword(EmailRegisto.toString(), PasswordRegisto.toString())
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
                                Toast.makeText(baseContext, "Falhou ao entrar",
                                        Toast.LENGTH_SHORT).show()

                            }
                        }
            }


            textViewRegistarReturnLogin.setOnClickListener {

                val intent = Intent(this, ActivityLogin::class.java)
                startActivity(intent)
            }

        }

    }
}


