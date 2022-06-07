package game.ceelo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import game.ceelo.CeeloController.loadLocalGame
import game.ceelo.databinding.ActivityMainBinding.inflate


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflate(layoutInflater).apply {
            setContentView(root)
            loadLocalGame(activity = this@MainActivity)
        }
    }
}