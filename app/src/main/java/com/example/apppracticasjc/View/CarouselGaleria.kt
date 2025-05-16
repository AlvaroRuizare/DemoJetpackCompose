package com.example.apppracticasjc.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.apppracticasjc.Data.RoomDB.BaseDatos
import com.example.apppracticasjc.Data.RoomDB.MultimediaEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarouselGaleria(){
    val contexto = LocalContext.current
    val multimediaDao = BaseDatos.getDatabase(contexto).multimediaDao()

    var listaFotos by remember { mutableStateOf(listOf<MultimediaEntity>()) }

    LaunchedEffect(Unit) {
        listaFotos = withContext(Dispatchers.IO) {
            multimediaDao.getAll()
        }
    }

    HorizontalUncontainedCarousel(
        state = rememberCarouselState {
            listaFotos.count()
        },
        itemWidth = 400.dp,
        itemSpacing = 15.dp,
        modifier = Modifier.height(500.dp)
    ) { index ->
        val value =listaFotos[index].ruta

        Image(
            painter = rememberAsyncImagePainter(value),
            contentDescription = "foto carousel",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .maskClip(MaterialTheme.shapes.extraLarge)
        )
    }
}

