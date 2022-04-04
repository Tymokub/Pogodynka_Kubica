package com.example.pogodynka

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.squareup.picasso.Picasso
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class youngFragment : Fragment() {

    //Viewmodel w celu większej czytelności kodu oraz dynamicznego wyświetlania danych
    private lateinit var myoungfragmentViewModel: youngfragmentViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        myoungfragmentViewModel=ViewModelProvider(this).get(youngfragmentViewModel::class.java)
        val view=inflater.inflate(R.layout.fragment_young,container,false)
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Logika aplikacji, pobieranie danych przy pomocy funkcji z viewmodelu oraz przypisywanie ich do odpowiednich pól tekstowych
        view.findViewById<Button>(R.id.fetch_button).setOnClickListener {
            var city=view.findViewById<EditText>(R.id.city_txtview).text.toString()
            //Asynchroniczność w celu uniknięcia błędów i dynamiczego aktualizowania się pól tekstowych
            doAsync {
                //Działania w tle
                var pressure=myoungfragmentViewModel.ReturnPressure(city)
                var sunset=myoungfragmentViewModel.ReturnSunset(city)
                var sunrise=myoungfragmentViewModel.ReturnSunrise(city)
                var datatime=myoungfragmentViewModel.ReturnDataTime()
                var temp=myoungfragmentViewModel.ReturnTemp(city)
                var icon=myoungfragmentViewModel.ReturnIcon(city)
                var description=myoungfragmentViewModel.ReturnDescription(city)
                uiThread{
                    //Działania po
                    view.findViewById<TextView>(R.id.press_txtview).setText(pressure+" hPa")
                    view.findViewById<TextView>(R.id.temp_txtview).setText(temp+" C")
                    view.findViewById<TextView>(R.id.datatime_txtview).setText(datatime)
                    view.findViewById<TextView>(R.id.sunrise_txtview).setText(sunrise)
                    view.findViewById<TextView>(R.id.sunset_txtview).setText(sunset)
                    view.findViewById<TextView>(R.id.weather_txtview).setText(description)
                    view.findViewById<TextView>(R.id.textView8).setText("Sunrise")
                    view.findViewById<TextView>(R.id.textView9).setText("Sunset")
                    view.findViewById<ImageView>(R.id.imagepress).setImageResource(R.drawable.ic_baseline_compress_24)
                    view.findViewById<ImageView>(R.id.imagesun).setImageResource(R.drawable.ic_baseline_wb_sunny_24)
                    view.findViewById<ImageView>(R.id.imagetemp).setImageResource(R.drawable.ic_baseline_device_thermostat_24)

                    //Dynamiczne przypisywanie odpowiedniej ikony do ImageView
                    Picasso.get().load("https://openweathermap.org/img/wn/${icon}@2x.png").into(view.findViewById<ImageView>(R.id.imageicon))



                }
            }




        }
        //Nawigacja między widokami
        view.findViewById<Button>(R.id.button_change).setOnClickListener {

            view.findNavController().navigate(R.id.action_youngFragment_to_fragment_old)

        }
    }





}