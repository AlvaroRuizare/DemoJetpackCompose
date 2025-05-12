package com.example.apppracticasjc.View

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.apppracticasjc.Data.Model.LocalSnackbarHostState
import com.example.apppracticasjc.Data.RoomDB.BaseDatos
import com.example.apppracticasjc.ViewModel.RegistroViewModel
import com.example.apppracticasjc.ViewModel.RegistroViewModelFactory
import java.time.LocalDate

@Composable
fun Registro(navController: NavHostController) {
    val context = LocalContext.current
    val registroViewModel : RegistroViewModel = viewModel( // ViewModel global que sobrevive a cambios de configuracion
        factory = RegistroViewModelFactory(
            BaseDatos.getDatabase(context).usuarioDao(),
            BaseDatos.getDatabase(context).tipoUsuarioDao()
        )
    )
    val registroUiState by registroViewModel.estadoPublico.collectAsState()
    val snackbarHostState = LocalSnackbarHostState.current

    LaunchedEffect(Unit) {
        registroViewModel.eventosUI.collect { mensaje ->
            snackbarHostState.showSnackbar(mensaje)
        }
    }

    Column(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ImagenLogo()
        Spacer(modifier = Modifier.height(10.dp))

        TextoErrorLogin(registroUiState.textoError)
        Spacer(modifier = Modifier.height(5.dp))

        CampoFormulario(
            "Usuario",
            {registroViewModel.alEditarRegistro(
                    it,
                    registroUiState.valorCampoContrasena,
                    registroUiState.valorCampoContrasena2,
                    registroUiState.valorCampoCorreo,
                    registroUiState.valorCampoFecha,
                    registroUiState.valorTipoUsuario
            )}, // Función al editar
            registroUiState.valorCampoUsuario,
            VisualTransformation.None,
            KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
            false
        )

        CampoFormulario(
            "Contraseña",
            {registroViewModel.alEditarRegistro(
                registroUiState.valorCampoUsuario,
                it,
                registroUiState.valorCampoContrasena2,
                registroUiState.valorCampoCorreo,
                registroUiState.valorCampoFecha,
                registroUiState.valorTipoUsuario
            )}, // Función al editar
            registroUiState.valorCampoContrasena,
            PasswordVisualTransformation(),
            KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
            false
        )

        CampoFormulario(
            "Repetir contraseña",
            {registroViewModel.alEditarRegistro(
                registroUiState.valorCampoUsuario,
                registroUiState.valorCampoContrasena,
                it,
                registroUiState.valorCampoCorreo,
                registroUiState.valorCampoFecha,
                registroUiState.valorTipoUsuario
            )}, // Función al editar
            registroUiState.valorCampoContrasena2,
            PasswordVisualTransformation(),
            KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
            false
        )

        CampoFormulario(
            "Correo",
            {registroViewModel.alEditarRegistro(
                registroUiState.valorCampoUsuario,
                registroUiState.valorCampoContrasena,
                registroUiState.valorCampoContrasena2,
                it,
                registroUiState.valorCampoFecha,
                registroUiState.valorTipoUsuario
            )}, // Función al editar
            registroUiState.valorCampoCorreo,
            VisualTransformation.None,
            KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
            false
        )

        CampoDatePicker(
            "Fecha de Nacimiento",
            {registroViewModel.alEditarRegistro(
                registroUiState.valorCampoUsuario,
                registroUiState.valorCampoContrasena,
                registroUiState.valorCampoContrasena2,
                registroUiState.valorCampoCorreo,
                it,
                registroUiState.valorTipoUsuario
            )},
            registroViewModel,
            Modifier
        )

        DesplegableTiposUsuario(
            "Tipos de Usuario",
            {registroViewModel.alEditarRegistro(
                registroUiState.valorCampoUsuario,
                registroUiState.valorCampoContrasena,
                registroUiState.valorCampoContrasena2,
                registroUiState.valorCampoCorreo,
                registroUiState.valorCampoFecha,
                it
            )},
            registroUiState.valorTipoUsuario,
            registroUiState.listaTiposUsuario,
            Modifier.padding(5.dp)
        )

        TextoBoton(
            "Ya tengo cuenta",
            {registroViewModel.navegarLogin(navController)} // Función al pulsar texto
        )

        BotonSiguiente(
            "Crear cuenta",
            {registroViewModel.pulsarCrearCuenta()}, // Función al pulsar botón
            registroUiState.botonHabilitado
        )
    }

}

@Composable
fun CampoDatePicker(
    textoLabel : String,
    funcionEditar : (String) -> Unit,
    registroViewModel: RegistroViewModel,
    modifier: Modifier = Modifier
) {
    var fechaSeleccionada by remember { mutableStateOf<Long?>(null) }
    LaunchedEffect(fechaSeleccionada) {
        fechaSeleccionada?.let {
            funcionEditar(registroViewModel.milisegundosAFecha(it))
        }
    }

    var mostrarCalendario by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = fechaSeleccionada?.let {
            registroViewModel.milisegundosAFecha(it)
        } ?: "",
        onValueChange = {  },
        label = { Text(textoLabel) },
        placeholder = { Text("dd/mm/aaaa") },
        trailingIcon = {
            Icon(Icons.Default.DateRange, contentDescription = "Select date")
        },
        modifier = modifier
            .padding(5.dp)
            .fillMaxWidth()
            .pointerInput(fechaSeleccionada) {
                awaitEachGesture {
                    // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                    // in the Initial pass to observe events before the text field consumes them
                    // in the Main pass.
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        mostrarCalendario = true
                    }
                }
            }
    )

    if (mostrarCalendario) {
        DatePickerCalendario(
            funcionSeleccionar = { fechaSeleccionada = it },
            funcionSalir = { mostrarCalendario = false }
        )
    }
}

// Objeto que utiliza el datepicker para no permitir fechas futuras
@OptIn(ExperimentalMaterial3Api::class)
object FechasPasadasOPresente: SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis <= System.currentTimeMillis()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun isSelectableYear(year: Int): Boolean {
        return year <= LocalDate.now().year
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerCalendario(
    funcionSeleccionar: (Long?) -> Unit,
    funcionSalir: () -> Unit
) {
    val datePickerState = rememberDatePickerState(selectableDates = FechasPasadasOPresente)

    DatePickerDialog(
        onDismissRequest = funcionSalir,
        confirmButton = {
            TextButton(onClick = {
                funcionSeleccionar(datePickerState.selectedDateMillis)
                funcionSalir()
            }) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(onClick = funcionSalir) {
                Text("Cancelar")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DesplegableTiposUsuario(
    label: String,
    onValueChangedEvent: (String) -> Unit,
    selectedValue: String,
    listaTipos: List<String>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedValue,
            onValueChange = {},
            label = { Text(text = label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            listaTipos.forEach { option: String ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        expanded = false
                        onValueChangedEvent(option)
                    }
                )
            }
        }
    }
}



