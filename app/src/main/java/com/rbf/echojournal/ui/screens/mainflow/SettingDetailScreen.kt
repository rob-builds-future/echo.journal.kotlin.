package com.rbf.echojournal.ui.screens.mainflow

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rbf.echojournal.R
import com.rbf.echojournal.ui.components.mainflow.settingsScreen.SettingType
import com.rbf.echojournal.ui.components.mainflow.settingsScreen.settingDetailScreens.ProfileSettingInfo
import com.rbf.echojournal.ui.components.mainflow.settingsScreen.settingDetailScreens.ProfileSettingLanguage
import com.rbf.echojournal.ui.components.mainflow.settingsScreen.settingDetailScreens.ProfileSettingReminder
import com.rbf.echojournal.ui.components.mainflow.settingsScreen.settingDetailScreens.ProfileSettingTemplate
import com.rbf.echojournal.ui.components.mainflow.settingsScreen.settingDetailScreens.ProfileSettingTheme
import com.rbf.echojournal.ui.components.mainflow.settingsScreen.settingDetailScreens.ProfileSettingUsername
import com.rbf.echojournal.ui.viewModel.AuthViewModel
import com.rbf.echojournal.ui.viewModel.LanguageViewModel
import com.rbf.echojournal.ui.viewModel.PrefsViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingDetailScreen(
    type: SettingType,
    onBack: () -> Unit,
    onLogoutConfirmed: () -> Unit = {},
    onProfileDeleted: () -> Unit = {}
) {
    // AuthViewModel holen
    val authViewModel: AuthViewModel = koinViewModel()
    val user by authViewModel.user.collectAsState()

    // PrefsViewModel holen, um aktuelle Einstellungen zu lesen
    val prefsViewModel: PrefsViewModel = koinViewModel()
    val currentLanguageCode by prefsViewModel.currentLanguage.collectAsState()

    // LanguageViewModel für die Liste aller Sprachen
    val languageViewModel: LanguageViewModel = koinViewModel()
    val allLanguages by languageViewModel.localizedLanguages.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (type) {
                            SettingType.Username       -> stringResource(R.string.detail_title_username)
                            SettingType.TargetLanguage -> stringResource(R.string.detail_title_target_language)
                            SettingType.ProfileInfo    -> stringResource(R.string.detail_title_profile_info)
                            SettingType.Theme          -> stringResource(R.string.detail_title_theme)
                            SettingType.Templates      -> stringResource(R.string.detail_title_templates)
                            SettingType.Reminders      -> stringResource(R.string.detail_title_reminders)
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Zurück"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            when (type) {
                SettingType.Username -> {
                    ProfileSettingUsername()
                }
                SettingType.TargetLanguage -> {
                    ProfileSettingLanguage()
                }
                SettingType.ProfileInfo -> {
                    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                    val memberSince = user
                        ?.createdAt
                        ?.toInstant()
                        ?.atZone(ZoneId.systemDefault())
                        ?.toLocalDate()
                        ?.format(dateFormatter)
                        ?: "–"
                    val languageName = allLanguages
                        .firstOrNull { it.code == currentLanguageCode }
                        ?.name
                        ?: currentLanguageCode.ifBlank { "–" }

                    ProfileSettingInfo(
                        memberSince = memberSince,
                        username = user?.username ?: "–",
                        language = languageName,
                        onLogout = {
                            authViewModel.signOut()
                            onLogoutConfirmed()
                        },
                        onDeleteConfirmed = {
                            authViewModel.deleteProfile { success ->
                                if (success) {
                                    onProfileDeleted()
                                } else {
                                    // Fehler: Snackbar/Dialog anzeigen (optional)
                                }
                            }
                        }
                    )
                }
                SettingType.Theme -> {
                    ProfileSettingTheme()
                }
                SettingType.Templates -> {
                    ProfileSettingTemplate()
                }
                SettingType.Reminders -> {
                    ProfileSettingReminder()
                }
            }
        }
    }
}
