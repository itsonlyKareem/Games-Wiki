package views

import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Xml
import android.widget.Button
import com.omega.gamestar.R

class IntroActivity : AppCompatActivity() {
    lateinit var getStarted: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        getViews()


    }

    private fun getViews() {
        getStarted = findViewById(R.id.introButton)
    }
}