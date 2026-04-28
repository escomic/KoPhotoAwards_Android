package com.devsimtaku.kophoto.core.designsystem.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun KPErrorDialog(
    title: String = "에러",
    message: String,
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit = onDismissRequest
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = title) },
        text = { Text(text = message) },
        confirmButton = {
            TextButton(onClick = onConfirmClick) {
                Text(text = "확인")
            }
        }
    )
}
