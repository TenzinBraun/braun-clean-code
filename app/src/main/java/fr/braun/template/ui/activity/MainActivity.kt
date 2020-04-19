package fr.braun.template.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import fr.braun.template.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpToolbar()
    }

    private fun setUpToolbar() {
        setSupportActionBar(appToolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_button_arrow_back)
        appToolbar.setNavigationOnClickListener {
            onNavigateUp()
        }
    }

    override fun onNavigateUp(): Boolean {
        return findNavController(R.id.appFragmentContainer).navigateUp()
    }


}
