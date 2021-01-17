package projetoG5.ipca.medutent

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registo.*
import kotlinx.android.synthetic.main.user_search_item_layout.*

class ActivityRegisto : AppCompatActivity(){

    val auth = FirebaseAuth.getInstance();

    private var color: Int= R.color.weak


    private lateinit var  mAuth: FirebaseAuth
    private lateinit var  refUsers : DatabaseReference
    private var firebaseUserID: String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registo)

        val email = findViewById<EditText>(R.id.EmailRegisto)
        val password = findViewById<EditText>(R.id.PasswordRegisto)
        val passwordConfirm = findViewById<EditText>(R.id.PasswordRegistoConfirm)
        val btnRegister = findViewById<Button>(R.id.buttonRegistarUtilizador)

        val passwordStrength = PasswordStrength()
        password.addTextChangedListener(passwordStrength)

        passwordStrength.strengthLevel.observe(this, Observer {strenghtLevel ->
            displayStrenghtLevel(strenghtLevel)
        })

        passwordStrength.strengthColor.observe(this, Observer {strenghtColor ->
            color = strenghtColor
        })


        btnRegister.setOnClickListener{

            if (email.text.toString() == "" || password.text.toString() == ""|| passwordConfirm.text.toString() == "")
            {
                Toast.makeText(this, "Credencias Vazias", Toast.LENGTH_SHORT).show()
            }

            else if(password.text.toString() != passwordConfirm.text.toString())
            {
                Toast.makeText(this,"As Palavras pass não são iguais",Toast.LENGTH_SHORT).show()
            }
            else if(password.text.toString().length < 6 || passwordConfirm.text.toString().length > 12)
            {
                Toast.makeText(this,"Password tem que ter entre 6 a 12 caracteres",Toast.LENGTH_SHORT).show()
            }

            else
            {




                auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful){

                firebaseUserID = mAuth.currentUser!!.uid
                refUsers = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUserID)

                val userHashMap = HashMap<String, Any>()
                userHashMap["uid"] = firebaseUserID
                userHashMap["username"] = username
                userHashMap["profile"] = "https://firebasestorage.googleapis.com/v0/b/messegerapp-d957e.appspot.com/o/profile-icons-user.jpeg?alt=media&token=428006b0-d8c8-4295-aa67-4a955146b2c5"
                userHashMap["cover"]   = "https://firebasestorage.googleapis.com/v0/b/messegerapp-d957e.appspot.com/o/profile-imag.jfif?alt=media&token=f7a52a32-8169-4061-b0f5-d3f4552fb5cc"
                userHashMap["status"] = "offline"
                userHashMap["search"] = username //.toLowerCase() a resolver


                refUsers.updateChildren(userHashMap)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(baseContext, "Register success.", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(baseContext, "register failed.", Toast.LENGTH_SHORT).show()

                            }

                        }

                            }

                        }

            }

        }
    }

    private fun displayStrenghtLevel(strenghtLevel: String) {
        buttonRegistarUtilizador.isEnabled = strenghtLevel.contains("Forte")

        strength_level_txt.text = strenghtLevel
        strength_level_txt.setTextColor(ContextCompat.getColor(this, color))
    }

}
