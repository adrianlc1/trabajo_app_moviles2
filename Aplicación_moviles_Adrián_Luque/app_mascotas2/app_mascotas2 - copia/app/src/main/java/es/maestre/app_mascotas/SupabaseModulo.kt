package es.maestre.app_mascotas

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModulo {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            // Tus credenciales para entrar a tu proyecto de Supabase
            supabaseUrl = "https://ipczrmxewgqrgaffjdly.supabase.co",

            supabaseKey = "sb_secret_XC3qMN5xYxCU8hiVb5ZVOA_L0Xa5SKE"
        ) {
            install(Postgrest) // Activa la base de datos
            install(Storage) // Activa el almacenamiento
        }
    }

    //Permite que los Repositorios usen la base de datos directamente
    @Provides
    @Singleton
    fun providePostgrest(client: SupabaseClient): Postgrest {
        return client.postgrest
    }
    //Permite que los Repositorios usen el almacenamiento de fotos
    @Provides
    @Singleton
    fun provideStorage(client: SupabaseClient): Storage {
        return client.storage
    }
}