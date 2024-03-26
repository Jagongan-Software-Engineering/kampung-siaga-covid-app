package com.seadev.aksi.model.ui.screen.hotline

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
import com.seadev.aksi.model.ui.component.hotline.ItemHotline
import com.seadev.aksi.model.ui.component.hotline.SkeletonHotline
import com.seadev.aksi.model.ui.theme.AksiKampungSiagaCovidTheme
import com.seadev.aksi.model.utils.AksiNav

@Composable
fun HotlineScreen(
    navController: NavController,
    hotlineViewModel: HotlineViewModel = hiltViewModel()
) {
    val hotlines by hotlineViewModel.hotlines.collectAsStateWithLifecycle()
    val provinces by hotlineViewModel.provinces.collectAsStateWithLifecycle()
    val isLoading by hotlineViewModel.isLoading.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        hotlineViewModel.getListHotline()
        hotlineViewModel.getListProvince()
    }
    Scaffold(
        topBar = {
            AksiSearchTopBar(
                title = "Hotline Response Covid-19",
                searchPlaceHolder = "Cari Provinsi",
                onBack = {
                    if (navController.previousBackStackEntry != null) navController.popBackStack()
                    else navController.navigate(AksiNav.HOME) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                },
                onSearch = hotlineViewModel::search,
                onClose = hotlineViewModel::resetData
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
                SkeletonHotline()
            } else items(provinces, key = { p -> p.id }) { province ->
                ItemHotline(province, hotlines.filter { h -> h.provinceId == province.id })
            }
        }
    }
}

@Preview
@Composable
private fun PreviewHotlineScreen() {
    AksiKampungSiagaCovidTheme {
        HotlineScreen(rememberNavController())
    }
}