package pantallas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun GaleriaItem(imagenUrl: String?) {
    //La tarjeta donde ira la foto del carrusel
    Card(
        modifier = Modifier.size(140.dp).padding(8.dp), // Define el tamaño del cuadrado y el espacio de separación
        shape = RoundedCornerShape(24.dp), // Redondea las esquinas
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant) //Le da un efecto al borde para que resalte
    ) {
        AsyncImage(model = imagenUrl, // La dirección URL de la imagen en la base de datos
            contentDescription = null,  //Es para ponerle una descripcion
            contentScale = ContentScale.Crop) //Recorta la imagen para que llene todo el cuadrado
    }
}