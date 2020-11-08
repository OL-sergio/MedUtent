package projetoG5.ipca.medutent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

@Suppress("DEPRECATION")
class ActivityIntro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        Handler().postDelayed({

            val intent = Intent(this, ActivityRegisto::class.java)
            startActivity(intent)
        },3000)


    }
}