package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun ProfileUserForm(
    header: String,
    name: String,
    email: String = "",
    password: String,
    confirmPassword: String,
    avatarUrl: String = "",
    isEditing: Boolean,
    nameError: String? = null,
    emailError: String? = null,
    passwordError: String? = null,
    confirmPasswordError: String? = null,
    avatarUrlError: String? = null,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onAvatarUrlChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(header, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(
            value = name,
            onValueChange = { onNameChange(it) },
            label = "Nombre",
            error = nameError,
            keyboardAction = ImeAction.Next,
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (!isEditing) {
            CustomTextField(
                value = email,
                onValueChange = { onEmailChange(it) },
                label = "Email",
                error = emailError,
                keyboardAction = ImeAction.Next,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (isEditing) {
            CustomTextField(
                value = avatarUrl,
                onValueChange = { onAvatarUrlChange(it) },
                label = "Avatar URL",
                error = avatarUrlError,
                keyboardAction = ImeAction.Next,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        CustomTextField(
            value = password,
            onValueChange = { onPasswordChange(it) },
            label = "Contraseña",
            isPassword = true,
            error = passwordError,
            keyboardAction = ImeAction.Next,
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(
            value = confirmPassword,
            onValueChange = { onConfirmPasswordChange(it) },
            label = "Confirmar contraseña",
            isPassword = true,
            error = confirmPasswordError,
            keyboardAction = ImeAction.Done,
        )
    }
}
