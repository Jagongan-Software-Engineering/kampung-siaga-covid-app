package com.seadev.aksi.model.ui.screen.main

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.seadev.aksi.model.ui.component.main.CardCampaign
import com.seadev.aksi.model.ui.component.main.CardLatestAssessment
import com.seadev.aksi.model.ui.component.main.CardMenuMain
import com.seadev.aksi.model.ui.component.main.ChartMain
import com.seadev.aksi.model.ui.component.main.GridSummaryCase
import com.seadev.aksi.model.ui.component.main.MainTopBar
import com.seadev.aksi.model.ui.component.main.MenuMain
import com.seadev.aksi.model.ui.component.main.SkeletonChartMain
import com.seadev.aksi.model.ui.component.main.SkeletonGridSummaryCase
import com.seadev.aksi.model.ui.component.main.TitleAssessment
import com.seadev.aksi.model.ui.screen.assessment.AssessmentViewModel
import com.seadev.aksi.model.ui.theme.AksiKampungSiagaCovidTheme
import com.seadev.aksi.model.utils.AksiNav
import com.seadev.aksi.model.utils.DateUtils.isToday

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    assessmentViewModel: AssessmentViewModel = hiltViewModel()
) {
    val summaryCase by mainViewModel.summaryCase.collectAsStateWithLifecycle()
    val chartLoading by mainViewModel.isLoading.collectAsStateWithLifecycle()
    val historyAssessment by assessmentViewModel.listAssessment.collectAsStateWithLifecycle()
    val isSnowCamping by remember {
        derivedStateOf {
            if (historyAssessment.isNotEmpty()) {
                !historyAssessment.first().dateCreated.isToday
            } else true
        }
    }
    LaunchedEffect(Unit) {
        mainViewModel.getSummary()
        assessmentViewModel.getHistoryAssessment()
    }
    Scaffold(
        topBar = { MainTopBar { navController.navigate(AksiNav.PROFILE) } }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (isSnowCamping) item {
                CardCampaign { navController.navigate(AksiNav.ASSESSMENT_ROUTE) }
            }

            if (historyAssessment.isNotEmpty()) {
                item { TitleAssessment(navController) }
                item { CardLatestAssessment(navController, historyAssessment.first()) }
            }
            item {
                CardMenuMain { menu ->
                    when (menu) {
                        MenuMain.Distribution -> navController.navigate(AksiNav.DISTRIBUTION)
                        MenuMain.Hotline -> navController.navigate(AksiNav.HOTLINE)
                        MenuMain.Prevention -> navController.navigate(AksiNav.PREVENTION)
                        MenuMain.Assessment -> navController.navigate(AksiNav.ASSESSMENT_ROUTE)
                    }
                }
            }
            item {
                if (chartLoading) SkeletonChartMain() else ChartMain(summaryCase)
            }
            item {
                if (chartLoading) SkeletonGridSummaryCase() else GridSummaryCase(summaryCase)
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun PreviewMainScreen() {
    AksiKampungSiagaCovidTheme {
        MainScreen(rememberNavController())
    }
}