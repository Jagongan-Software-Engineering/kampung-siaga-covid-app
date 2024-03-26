package com.seadev.aksi.model.ui.screen.register

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.seadev.aksi.model.domain.model.AksiUser
import com.seadev.aksi.model.ui.screen.login.AuthViewModel
import com.seadev.aksi.model.ui.theme.AksiColor
import com.seadev.aksi.model.ui.theme.AksiKampungSiagaCovidTheme
import com.seadev.aksi.model.utils.AksiNav
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    var currentUser by remember { mutableStateOf(AksiUser.Init) }
    val context = LocalContext.current
    val authMessage by authViewModel.message.collectAsStateWithLifecycle()

    LaunchedEffect(authMessage) {
        if (authMessage.isNotBlank()) Toast.makeText(context, authMessage, Toast.LENGTH_SHORT)
            .show()
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val (btnNext, contentRef) = createRefs()
            val pagerState = rememberPagerState { 2 }
            val coroutineScope = rememberCoroutineScope()
            var btnActive by remember { mutableStateOf(false) }
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false,
                modifier = Modifier.constrainAs(contentRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(btnNext.top, 16.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
            ) { page ->
                if (page == 0) CreateAccountContent { isValid, user ->
                    btnActive = isValid
                    currentUser = currentUser.copy(
                        fullName = user.fullName,
                        email = user.email,
                        phoneNumber = user.phoneNumber,
                        nik = user.nik,
                        uid = user.uid
                    )
                }
                else InputAddressContent { isValid, user ->
                    btnActive = isValid
                    currentUser = currentUser.copy(
                        provinceId = user.provinceId,
                        cityId = user.cityId,
                        districtId = user.districtId,
                        subDistrictId = user.subDistrictId,
                        rtNumber = user.rtNumber,
                        rwNumber = user.rwNumber,
                        fullAddress = user.fullAddress
                    )
                }
            }
            Button(
                onClick = {
                    if (pagerState.currentPage == 1) authViewModel.createNewUser(currentUser) { isSuccess ->
                        if (isSuccess) navController.navigate(AksiNav.HOME) {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    } else coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AksiColor.Secondary
                ),
                enabled = btnActive,
                modifier = Modifier.constrainAs(btnNext) {
                    bottom.linkTo(parent.bottom, 16.dp)
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    width = Dimension.fillToConstraints
                }) {
                Text(
                    text = if (pagerState.currentPage == 0) "Buat Akun" else "Masuk",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewRegisterScreen() {
    AksiKampungSiagaCovidTheme {
        RegisterScreen(rememberNavController())
    }
}