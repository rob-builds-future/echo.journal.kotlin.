package com.rbf.echojournal.ui.components.mainflow.entryListScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rbf.echojournal.R
import com.rbf.echojournal.ui.theme.ColorManager
import com.rbf.echojournal.ui.viewModel.PrefsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun StatisticsHeader(
    modifier: Modifier = Modifier,
    totalWords: Int,
    totalMinutes: Int
) {
    val prefsViewModel: PrefsViewModel = koinViewModel()
    val themeName by prefsViewModel.theme.collectAsState()
    val echoColor = ColorManager.getColor(themeName)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Wort-Statistik
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector    = Icons.Default.FormatQuote,
                contentDescription = stringResource(R.string.contentdesc_quote),
                modifier       = Modifier.size(16.dp),
                tint = echoColor
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text     = totalWords.toString(),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color    = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text     = stringResource(R.string.text_words),
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color    = MaterialTheme.colorScheme.onBackground
            )
        }

        // Vertikaler Trenner
        VerticalDivider(
            modifier = Modifier
                .height(40.dp)
                .width(1.dp),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
        )

        // Zeit-Statistik
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector    = Icons.Default.Schedule,
                contentDescription = stringResource(R.string.contentdesc_timer),
                modifier       = Modifier.size(16.dp),
                tint = echoColor
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text     = totalMinutes.toString(),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color    = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text     = stringResource(R.string.text_minutes),
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color    = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
