package com.rbf.echojournal.ui.components.mainflow.entryListScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rbf.echojournal.R

@Composable
fun CongratsDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onFlameIconPositioned: (Offset) -> Unit,
    onNiceClick: (Offset) -> Unit,
    flameColor: Color
) {
    if (!show) return

    var flameIconOffset by remember { mutableStateOf(Offset.Zero) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = AlertDialogDefaults.shape
            ),
        containerColor = MaterialTheme.colorScheme.surface,
        icon = { /* optional */ },
        title = {
            Text(
                stringResource(R.string.congrats_title),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Whatshot,
                    contentDescription = null,
                    tint = flameColor,
                    modifier = Modifier
                        .size(32.dp)
                        .onGloballyPositioned { coords ->
                            val pos = coords.localToWindow(Offset.Zero)
                            val center = Offset(
                                pos.x + coords.size.width / 2,
                                pos.y + coords.size.height / 2
                            )
                            flameIconOffset = center
                            onFlameIconPositioned(center)
                        }
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    stringResource(R.string.congrats_message),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onNiceClick(flameIconOffset) },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(stringResource(R.string.button_nice))
            }
        }
    )
}