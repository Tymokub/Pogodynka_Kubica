package com.example.pogodynka

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.squareup.picasso.Picasso
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class Fragment_old : Fragment() {

    //Funkcja odpowiadająca za wibrowanie telefonu przy naciśnięciu przycisku
    fun Fragment.vibratePhone() {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(200)
        }
    }

    //Cała logika taka sama jak w fragmencie odpowiadającym za widok dla młodszego użytkownika
    private lateinit var myoungfragmentViewModel: youngfragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myoungfragmentViewModel= ViewModelProvider(this).get(youngfragmentViewModel::class.java)
        var view= inflater.inflate(R.layout.fragment_old, container, false)
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.findViewById<Button>(R.id.fetch_button).setOnClickListener {

            var city=view.findViewById<EditText>(R.id.city_txtview).text.toString()
            vibratePhone()
            doAsync {

                var pressure=myoungfragmentViewModel.ReturnPressure(city)
                var sunset=myoungfragmentViewModel.ReturnSunset(city)
                var sunrise=myoungfragmentViewModel.ReturnSunrise(city)
                var datatime=myoungfragmentViewModel.ReturnDataTime()
                var temp=myoungfragmentViewModel.ReturnTemp(city)
                var description=myoungfragmentViewModel.ReturnDescription(city)
                uiThread{
                    view.findViewById<TextView>(R.id.press_txtview).setText(pressure+" hPa")
                    view.findViewById<TextView>(R.id.temp_txtview).setText(temp+" C")
                    view.findViewById<TextView>(R.id.datatime_txtview).setText(datatime)
                    view.findViewById<TextView>(R.id.sunrise_txtview).setText(sunrise)
                    view.findViewById<TextView>(R.id.sunset_txtview).setText(sunset)
                    view.findViewById<TextView>(R.id.weather_txtview).setText(description)
                    view.findViewById<TextView>(R.id.textView8).setText("Sunrise: ")
                    view.findViewById<TextView>(R.id.textView9).setText("Sunset: ")
                    view.findViewById<TextView>(R.id.textView2).setText("Pressure: ")
                    view.findViewById<TextView>(R.id.textView3).setText("Degrees: ")
                    view.findViewById<TextView>(R.id.textView4).setText("Weather:")






                }
            }




        }
        view.findViewById<Button>(R.id.button_change).setOnClickListener {
            vibratePhone()
            view.findNavController().navigate(R.id.action_fragment_old_to_youngFragment)

        }
    }


}