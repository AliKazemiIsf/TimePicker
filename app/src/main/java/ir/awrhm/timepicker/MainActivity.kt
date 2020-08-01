package ir.awrhm.timepicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ir.awrhm.module.TimeDialogFragment
import ir.awrhm.module.utilities.setOnTime24PickedListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timePicker = TimeDialogFragment.newInstance()
        timePicker.setInitialTime24(12, 0)
        timePicker.setOnTime24PickedListener { time24 ->
            Toast.makeText(this@MainActivity,"${time24.hour} : ${time24.minute}", Toast.LENGTH_LONG).show()
        }
        timePicker.show(supportFragmentManager, "TimeDialog")
    }
}