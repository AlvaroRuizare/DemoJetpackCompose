package com.example.apppracticasjc.View

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.apppracticasjc.Data.Model.LocalSnackbarHostState
import com.example.apppracticasjc.Data.RoomDB.BaseDatos
import com.example.apppracticasjc.R
import com.example.apppracticasjc.ViewModel.LoginViewModel
import com.example.apppracticasjc.ViewModel.LoginViewModelFactory


@Composable
fun Login(navController: NavHostController) {
    val context = LocalContext.current
    val loginViewModel : LoginViewModel = viewModel( // ViewModel global que sobrevive a cambios de configuracion
        factory = LoginViewModelFactory(
            BaseDatos.getDatabase(context).usuarioDao(),
            BaseDatos.getDatabase(context).tipoUsuarioDao()
        )
    )
    val loginUiState by loginViewModel.estadoPublico.collectAsState()
    val snackbarHostState = LocalSnackbarHostState.current

    LaunchedEffect(Unit) {
        loginViewModel.eventosUI.collect { mensaje ->
            snackbarHostState.showSnackbar(mensaje)
        }
    }

    Column(
        modifier = Modifier.padding(top = 50.dp, start = 10.dp, end = 10.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ImagenLogo()
        Spacer(modifier = Modifier.height(10.dp))

        TextoErrorLogin(loginUiState.textoError)
        Spacer(modifier = Modifier.height(5.dp))

        CampoFormulario(
            "Usuario",
            {loginViewModel.alEditarLogin(it, loginUiState.valorCampoContrasena)},
            loginUiState.valorCampoUsuario,
            VisualTransformation.None,
            KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
            false
        )

        CampoFormulario(
            "Contraseña",
            {loginViewModel.alEditarLogin(loginUiState.valorCampoUsuario, it)},
            loginUiState.valorCampoContrasena,
            PasswordVisualTransformation(),
            KeyboardOptions(keyboardType = KeyboardType.Password),
            false
        )

        TextoBoton(
            "Crear cuenta",
            {loginViewModel.navegarRegistro(navController)}
        )

        BotonSiguiente(
            "Iniciar sesión",
            {loginViewModel.pulsarLogin(navController)},
            loginUiState.botonHabilitado
        )
    }
}

@Composable
fun ImagenLogo() {
    Icon(
        painter = painterResource(R.drawable.login),
        tint = MaterialTheme.colorScheme.primary,
        contentDescription = "Imagen principal"
    )
}

@Composable
fun TextoErrorLogin(textoError : String) {
    Text(text = textoError,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.error)
}

@Composable
fun CampoFormulario(
    textoLabel : String,
    funcionRecibida: (String) -> Unit,
    valorCampo : String,
    tipoCampo : VisualTransformation,
    tipoTeclado : KeyboardOptions,
    editable : Boolean
) {
    OutlinedTextField(
        value = valorCampo,
        onValueChange = { funcionRecibida(it) },
        modifier = Modifier.fillMaxWidth().padding(5.dp),
        singleLine = true,
        label = {Text(text = textoLabel)},
        visualTransformation = tipoCampo,
        keyboardOptions = tipoTeclado,
        readOnly = editable
    )
}

@Composable
fun TextoBoton(
    textoBoton: String,
    funcionRecibida: () -> Unit
) {
    Text(text = textoBoton,
        modifier = Modifier.padding(10.dp).clickable { funcionRecibida()  },
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.tertiary)
}

@Composable
fun BotonSiguiente(
    textoBoton: String,
    funcionRecibida: () -> Unit,
    habilitado : Boolean
) {
    OutlinedButton(
        onClick = { funcionRecibida() },
        enabled = habilitado,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .height(48.dp),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
    ){
        Text(text = textoBoton,
            fontSize = 17.sp)
    }
}
