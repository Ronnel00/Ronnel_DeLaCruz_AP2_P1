package edu.ucne.ronnel_delacruz_ap2_p1.presentation.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAmonestacionScreen(
    amonestacionId: Int?,
    onNavigateBack: () -> Unit,
    viewModel: EditAmonestacionViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(amonestacionId) {
        viewModel.onEvent(EditAmonestacionUiEvent.Load(amonestacionId))
    }

    LaunchedEffect(state.saved) {
        if (state.saved) onNavigateBack()
    }

    LaunchedEffect(state.deleted) {
        if (state.deleted) onNavigateBack()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(if (state.isNew) "Nueva Amonestación" else "Editar Amonestación") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(8.dp)
        ) {
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Nombres
                    OutlinedTextField(
                        value = state.nombres,
                        onValueChange = {
                            viewModel.onEvent(EditAmonestacionUiEvent.NombresChanged(it))
                        },
                        label = { Text("Nombres") },
                        isError = state.nombresError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    state.nombresError?.let {
                        Text(it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
                    }

                    Spacer(Modifier.height(8.dp))

                    // Razón
                    OutlinedTextField(
                        value = state.razon,
                        onValueChange = {
                            viewModel.onEvent(EditAmonestacionUiEvent.RazonChanged(it))
                        },
                        label = { Text("Razón") },
                        isError = state.razonError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    state.razonError?.let {
                        Text(it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
                    }

                    Spacer(Modifier.height(8.dp))

                    // Monto
                    OutlinedTextField(
                        value = state.monto?.toString() ?: "",
                        onValueChange = {
                            viewModel.onEvent(EditAmonestacionUiEvent.MontoChanged(it))
                        },
                        label = { Text("Monto") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        isError = state.montoError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    state.montoError?.let {
                        Text(it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
                    }

                    Spacer(Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        OutlinedButton(
                            onClick = { viewModel.onEvent(EditAmonestacionUiEvent.Save) },
                            enabled = !state.isSaving
                        ) {
                            if (state.isSaving) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Icon(Icons.Default.Edit, contentDescription = null)
                            }
                            Spacer(Modifier.width(4.dp))
                            Text(if (state.isNew) "Guardar" else "Actualizar")
                        }

                        if (!state.isNew) {
                            OutlinedButton(
                                onClick = { viewModel.onEvent(EditAmonestacionUiEvent.Delete) },
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = Color.Red
                                )
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                                Spacer(Modifier.width(4.dp))
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditAmonestacionPreview() {
    MaterialTheme {
        EditAmonestacionScreen(amonestacionId = null, onNavigateBack = {})
    }
}