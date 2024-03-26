package com.seadev.aksi.model.ui.screen.distribution

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.seadev.aksi.model.ui.component.AksiSearchTopBar
import com.seadev.aksi.model.ui.component.distribution.ItemDistribution
import com.seadev.aksi.model.ui.component.distribution.SkeletonItemDistribution
import com.seadev.aksi.model.ui.theme.AksiKampungSiagaCovidTheme

@Composable
fun DistributionScreen(
    navController: NavController,
    distributionViewModel: DistributionViewModel = hiltViewModel()
) {
    val isLoading by distributionViewModel.isLoading.collectAsStateWithLifecycle()
    val listDistributions by distributionViewModel.provinceDistribution.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { distributionViewModel.getProvinceDistribution() }
    Scaffold(
        topBar = {
            AksiSearchTopBar(
                title = "Distribusi Per-Provinsi",
                searchPlaceHolder = "Cari Provinsi",
                onBack = navController::popBackStack,
                onSearch = distributionViewModel::search,
                onClose = distributionViewModel::resetList
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (isLoading) items(10) {
                SkeletonItemDistribution()
            } else items(listDistributions, key = { p -> p.province }) { distribution ->
                ItemDistribution(case = distribution)
            }
        }
    }
}

@Preview
@Composable
private fun PreviewDistributionScreen() {
    AksiKampungSiagaCovidTheme {
        DistributionScreen(rememberNavController())
    }
}