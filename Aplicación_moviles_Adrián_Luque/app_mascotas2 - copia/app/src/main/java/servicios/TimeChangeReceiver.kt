package servicios

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.datetime.DateTimeUnit.Companion.DAY
import java.util.Calendar

class TimeChangeReceiver : BroadcastReceiver() {
    //Esta función se activa cada vez que  pasó un minuto.
    override fun onReceive(context: Context?, intent: Intent?) {
        //Para saber si es de noche
        if (isNightTime()) {
            setDarkTheme(context) // Si es de noche se pone en modo oscuro
        } else {
            setLightTheme(context) //Si no es de noche se pone en modo claro
        }
    }
    private fun isNightTime () : Boolean {
        // Obtenemos la hora actual del móvil
        val currentTime =
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        // Devuelve true  si la hora es mayor o igual a las 18:00 (6 PM)
        // O si es menor a las 6:00 AM (madrugada).
        return currentTime >= 18 || currentTime < 6
    }
    //Aplica el tema de noche a toda la aplicación.
    private fun setDarkTheme (context: Context?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        Log.i("TimeChangeReceiver", "setDarkTheme ON")
    }
    //Quita el modo noche y vuelve a los colores normales.
    private fun setLightTheme (context: Context?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        Log.i("TimeChangeReceiver", "setLightTheme ON")
    }
 }

