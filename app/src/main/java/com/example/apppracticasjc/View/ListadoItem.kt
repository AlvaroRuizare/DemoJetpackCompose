package com.example.apppracticasjc.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.apppracticasjc.Data.RoomDB.UsuarioEntity

@Composable
fun ListadoItem(
    usuario: UsuarioEntity,
    fotoUsuario: String,
    navegarAUsuario: (UsuarioEntity) -> Unit
) {
    Card (
        modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer)
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = rememberAsyncImagePainter(fotoUsuario),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp))
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = usuario.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Correo: ${usuario.correo}",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                )
                Text(
                    text = "Fecha nac.: ${usuario.fechaNacimiento}",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                )
                Text(
                    text = "Tipo: ${usuario.idTipoUsuario}",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                )
            }

            Checkbox(
                false,
                onCheckedChange = {  },
            )
        }
    }
}