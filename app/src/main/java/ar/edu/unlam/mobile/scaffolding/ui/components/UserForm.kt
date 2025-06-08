package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.domain.user.models.User

@Composable
fun ProfileUserForm(
    header: String,
    user: User?,
    isLoading: Boolean,
    onSaveChanges: (name: String, email: String?, avatarUrl: String?, password: String?) -> Unit,
) {
    val isEditing = user != null

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") } // solo para registro
    var avatarUrl by remember { mutableStateOf("") } // solo para edición
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    LaunchedEffect(key1 = user) {
        if (isEditing && user != null) {
            name = user.name
            avatarUrl = user.avatarUrl
            password = ""
            confirmPassword = ""
        } else {
            name = ""
            avatarUrl = ""
            password = ""
            confirmPassword = ""
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(header)
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(
            value = name,
            onValueChange = { name = it },
            label = "Nombre",
            keyboardAction = ImeAction.Next,
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (!isEditing) {
            CustomTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                keyboardAction = ImeAction.Next,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (isEditing) {
            CustomTextField(
                value = avatarUrl,
                onValueChange = { avatarUrl = it },
                label = "Avatar URL",
                keyboardAction = ImeAction.Next,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        CustomTextField(
            value = password,
            onValueChange = { password = it },
            label = "Contraseña",
            isPassword = true,
            keyboardAction = ImeAction.Next,
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = "Confirmar contraseña",
            isPassword = true,
            keyboardAction = ImeAction.Done,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onSaveChanges(
                    name,
                    if (isEditing) user.email else email,
                    if (isEditing) user.avatarUrl else avatarUrl,
                    password,
                )
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading,
        ) {
            Text(
                if (isLoading) {
                    "Guardando..."
                } else if (isEditing) {
                    "Actualizar"
                } else {
                    "Crear Cuenta"
                },
            )
        }
    }
}
