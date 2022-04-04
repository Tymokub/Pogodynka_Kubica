package com.example.pogodynka

import android.app.Application
import android.content.Context
import android.content.Context.*
import android.os.Build
import android.os.Vibrator
import android.os.VibrationEffect
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment


class youngfragmentViewModel (application: Application): AndroidViewModel(application) {



    //Poniższe funkcje odpowiadają za pobranie i zwrócenie konkretnych danych z API
    fun ReturnPressure(city:String):String{


            //Adres url ze zmiennym miastem
            var url =
                "https://api.openweathermap.org/data/2.5/weather?q=${city},pl&APPID=959841b018423a298a8d327cf53dd6b9"
            //Czytanie i odpowiednie sformatowanie danych dostarczonych przez API
            var resultJson = URL(url).readText()
            var jsonObj = JSONObject(resultJson)

            var pressure = jsonObj.getJSONObject("main").getString("pressure")

        return pressure
    }
    fun ReturnIcon(city:String):String{

        var url =
            "https://api.openweathermap.org/data/2.5/weather?q=${city},pl&APPID=959841b018423a298a8d327cf53dd6b9"
        var resultJson = URL(url).readText()
        var jsonObj = JSONObject(resultJson)
        var icon = jsonObj.getJSONArray("weather").getJSONObject(0).getString("icon")
        return icon

    }
    fun ReturnDescription(city:String):String{

        var url =
            "https://api.openweathermap.org/data/2.5/weather?q=${city},pl&APPID=959841b018423a298a8d327cf53dd6b9"
        var resultJson = URL(url).readText()
        var jsonObj = JSONObject(resultJson)
        var desc = jsonObj.getJSONArray("weather").getJSONObject(0).getString("description")
        return desc

    }

    fun ReturnTemp(city:String):String{

        var url =
            "https://api.openweathermap.org/data/2.5/weather?q=${city},pl&APPID=959841b018423a298a8d327cf53dd6b9"
        var resultJson = URL(url).readText()
        var jsonObj = JSONObject(resultJson)

        var temp = (jsonObj.getJSONObject("main").getString("temp").toFloat() - 273.15).toInt()
            .toString()
        return temp
    }

    fun ReturnSunset(city:String):String{

        var url =
            "https://api.openweathermap.org/data/2.5/weather?q=${city},pl&APPID=959841b018423a298a8d327cf53dd6b9"
        var resultJson = URL(url).readText()
        var jsonObj = JSONObject(resultJson)
        var sunset_val = jsonObj.getJSONObject("sys").getString("sunset").toLong()
        var timeD = Date(sunset_val * 1000)
        val sdf = SimpleDateFormat("HH:mm")
        var sunset = sdf.format(timeD)

        return sunset

    }
    fun ReturnSunrise(city:String):String{

        var url =
            "https://api.openweathermap.org/data/2.5/weather?q=${city},pl&APPID=959841b018423a298a8d327cf53dd6b9"
        var resultJson = URL(url).readText()
        var jsonObj = JSONObject(resultJson)
        val sdf = SimpleDateFormat("HH:mm")
        var sunrise_val = jsonObj.getJSONObject("sys").getString("sunrise").toLong()
        var timeD = Date(sunrise_val * 1000)
        var sunrise = sdf.format(timeD)

        return sunrise

    }

    //Funkcja zwracająca bieżącą datę oraz godzine
    @RequiresApi(Build.VERSION_CODES.O)
    fun ReturnDataTime(): String {


        var now = LocalDateTime.now()
        var formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm")
        var formatted = now.format(formatter)
        return formatted

    }
}