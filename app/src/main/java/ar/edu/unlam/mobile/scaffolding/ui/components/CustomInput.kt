package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false,
    keyboardAction: ImeAction,
) {
    val keyboardOptions =
        KeyboardOptions.Default.copy(
            imeAction = keyboardAction,
            keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text,
        )

    val visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        modifier =
            Modifier
                .fillMaxWidth(),
    )
}

@Preview(showBackground = true, name = "Multiple TextFields")
@Composable
fun MultipleCustomTextFieldsPreview() {
    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("Hola") }
    var password by remember { mutableStateOf("asd123") }

    Column(modifier = Modifier.padding(16.dp)) {
        CustomTextField(
            value = text1,
            onValueChange = { text1 = it },
            label = "Campo vacío",
            keyboardAction = ImeAction.Next,
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(
            value = text2,
            onValueChange = { text2 = it },
            label = "Campo con texto",
            keyboardAction = ImeAction.Done,
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(
            value = password,
            onValueChange = { password = it },
            label = "Contraseña",
            isPassword = true,
            keyboardAction = ImeAction.Send,
        )
    }
}
