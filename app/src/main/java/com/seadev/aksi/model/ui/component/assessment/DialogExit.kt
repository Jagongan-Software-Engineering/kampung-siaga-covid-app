package com.seadev.aksi.model.ui.component.assessment

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.seadev.aksi.model.ui.theme.AksiKampungSiagaCovidTheme


@Composable
fun AksiDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "")
        },
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = dialogTitle
            )
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Ya")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Tidak")
            }
        }
    )
}

@Preview
@Composable
private fun PreviewDialog() {
    AksiKampungSiagaCovidTheme {
        AksiDialog(
            dialogText = "Apakah ingin keluar dari penilaian diri dan hapus jawaban?",
            dialogTitle = "Keluar dari Penilaian Diri",
            icon = Icons.Rounded.Delete,
            onDismissRequest = { /*TODO*/ },
            onConfirmation = {}
        )
    }
}
