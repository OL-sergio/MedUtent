package projetoG5.ipca.medutent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView

class MainActivity : AppCompatActivity(), TextWatcher {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val password = findViewById<EditText>(R.id.PasswordRegisto) as EditText
        password.addTextChangedListener(this)
    }

    override fun afterTextChanged(s: Editable) {}
    override fun beforeTextChanged(
            s: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        updatePasswordStrengthView(s.toString())
    }

    private fun updatePasswordStrengthView(password: String) {

        val strengthView = findViewById<ProgressBar>(R.id.passwordLevel) as TextView
        if (TextView.VISIBLE != strengthView.visibility)
            return

        if (TextUtils.isEmpty(password)) {
            strengthView.text = ""
            return
        }

        val str = PasswordStrength.calculateStrength(password)
        strengthView.text = str.getText(this)
        strengthView.setTextColor(str.color)


        if (str.getText(this) == "Fraca") {

        } else if (str.getText(this) == "Média") {

        } else if (str.getText(this) == "Forte") {

        } else { (str.getText( this) == "Muito Forte")

        }
    }
}
