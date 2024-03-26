package com.seadev.aksi.model.ui.screen.assessment

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.seadev.aksi.model.ui.component.main.CardLatestAssessment
import com.seadev.aksi.model.ui.theme.AksiColor
import com.seadev.aksi.model.ui.theme.AksiKampungSiagaCovidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssessmentHistoryScreen(
    navController: NavController,
    assessmentViewModel: AssessmentViewModel = hiltViewModel()
) {
    val historyAssessment by assessmentViewModel.listAssessment.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) { assessmentViewModel.getHistoryAssessment() }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = navController::popBackStack) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Back")
                    }
                },
                title = {
                    Text(
                        modifier = Modifier,
                        text = "Riwayat Penilaian Diri",
                        fontWeight = FontWeight.Bold,
                        color = if (isSystemInDarkTheme()) AksiColor.Text else AksiColor.Primary
                    )
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(historyAssessment) { history ->
                CardLatestAssessment(navController, historyAssessment = history)
            }
        }
    }
}

@Preview
@Composable
private fun PreviewAssessmentHistoryScreen() {
    AksiKampungSiagaCovidTheme {
        AssessmentHistoryScreen(rememberNavController())
    }
}