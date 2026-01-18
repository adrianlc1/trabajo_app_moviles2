package servicios

import android.app.Service
import android.content.Intent
import android.content. IntentFilter
import android.os.IBinder

class NightModeService : Service () {
    // El Receiver es el  que reaccionará cuando cambie la hora.
    private lateinit var timeChangeReceiver: TimeChangeReceiver

    // onCreate se ejecuta una sola vez
    override fun onCreate() {
        super.onCreate()
        //Inicializamos el oyente del tiempo
        timeChangeReceiver = TimeChangeReceiver()
    }
    //se ejecuta cada vez que el servicio recibe la orden de empezar.
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        //Envia una señal al movil cada minuto
        val filter = IntentFilter(Intent.ACTION_TIME_TICK)
        //Procesa cada minuto que pasa
        registerReceiver(timeChangeReceiver, filter)
        //Es para abrir automaticamente el servicio
        return START_REDELIVER_INTENT
    }
    //Es para conectar la app con el servicio
    override fun onBind(intent: Intent): IBinder? {
        return null

    }
    //Esto funciona cuando el servicio se apaga.
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(timeChangeReceiver)
    }
}