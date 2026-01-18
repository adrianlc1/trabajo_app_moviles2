package conexion
import io.github.jan.supabase.createSupabaseClient


object SupabaseCliente {

    //Es la ubicación de tu proyecto en los servidores de Supabase.
    private const val SUPABASE_URL = "https://ipczrmxewgqrgaffjdly.supabase.co"

    //Es como la contraseña que permite que tu app se identifique.
    private const val SUPABASE_KEY = "sb_secret_XC3qMN5xYxCU8hiVb5ZVOA_L0Xa5SKE"

    val client = createSupabaseClient(

        supabaseUrl = SUPABASE_URL,

        supabaseKey = SUPABASE_KEY

    ) {

    }
}