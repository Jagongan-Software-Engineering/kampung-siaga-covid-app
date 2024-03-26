package com.seadev.aksi.model

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.seadev.aksi.model.ui.screen.assessment.AssessmentHistoryScreen
import com.seadev.aksi.model.ui.screen.assessment.AssessmentResultScreen
import com.seadev.aksi.model.ui.screen.assessment.AssessmentWalkthroughScreen
import com.seadev.aksi.model.ui.screen.assessment.SelfAssessmentScreen
import com.seadev.aksi.model.ui.screen.distribution.DistributionScreen
import com.seadev.aksi.model.ui.screen.hotline.HotlineScreen
import com.seadev.aksi.model.ui.screen.login.AuthViewModel
import com.seadev.aksi.model.ui.screen.login.LoginScreen
import com.seadev.aksi.model.ui.screen.main.MainScreen
import com.seadev.aksi.model.ui.screen.prevention.PreventionScreen
import com.seadev.aksi.model.ui.screen.prevstep.PreventionStepScreen
import com.seadev.aksi.model.ui.screen.profile.ProfileScreen
import com.seadev.aksi.model.ui.screen.register.AddressViewModel
import com.seadev.aksi.model.ui.screen.register.RegisterScreen
import com.seadev.aksi.model.ui.theme.AksiKampungSiagaCovidTheme
import com.seadev.aksi.model.utils.AksiNav
import com.seadev.aksi.model.utils.AksiNavArgument
import com.seadev.aksi.model.utils.DataUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var isSplashLoading = MutableStateFlow(true)
    private var isUserFound = MutableStateFlow(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val splashScreen = installSplashScreen()
        isSplashLoading.onEach {
            splashScreen.setKeepOnScreenCondition { it }
        }.launchIn(lifecycleScope)

        setContent {
            AksiKampungSiagaCovidTheme {
                val addressViewModel = hiltViewModel<AddressViewModel>()
                val isLoadingAddress by addressViewModel.isLoading.collectAsStateWithLifecycle()
                val authViewModel = hiltViewModel<AuthViewModel>()
                val isFound by isUserFound.collectAsStateWithLifecycle()
                LaunchedEffect(Unit) {
                    addressViewModel.getAllData()
                    Firebase.auth.currentUser?.let {
                        authViewModel.getUser(it.uid) { isFound ->
                            isUserFound.value = isFound
                        }
                    }
                }
                LaunchedEffect(isLoadingAddress) {
                    isSplashLoading.value = isLoadingAddress
                }

                MainRouter(isFound)
            }
        }
    }

    @Composable
    fun MainRouter(isUserFound: Boolean) {
        val navController = rememberNavController()
        val startDestination = if (isUserFound) AksiNav.HOME else AksiNav.AUTH_ROUTE
        NavHost(navController = navController, startDestination = startDestination) {
            composable(AksiNav.HOME) { MainScreen(navController) }
            composable(AksiNav.DISTRIBUTION) { DistributionScreen(navController) }
            composable(AksiNav.HOTLINE) { HotlineScreen(navController) }
            composable(AksiNav.PREVENTION) { PreventionScreen(navController) }
            composable(
                AksiNav.PREVENTION_STEP + "/{${AksiNavArgument.PREVENTION_INDEX}}",
                arguments = listOf(navArgument(AksiNavArgument.PREVENTION_INDEX) {
                    type = NavType.IntType
                })
            ) { backStackEntry ->
                PreventionStepScreen(
                    backStackEntry.arguments?.getInt(AksiNavArgument.PREVENTION_INDEX) ?: 0,
                    navController
                )
            }
            composable(AksiNav.PROFILE) { ProfileScreen(navController) }
            composable(AksiNav.ASSESSMENT_HISTORY) { AssessmentHistoryScreen(navController) }
            navigation(startDestination = AksiNav.LOGIN, route = AksiNav.AUTH_ROUTE) {
                composable(AksiNav.LOGIN) {
                    LoginScreen(navController)
                }
                composable(AksiNav.REGISTER) {
                    RegisterScreen(navController)
                }
            }
            navigation(startDestination = AksiNav.ASSESSMENT, route = AksiNav.ASSESSMENT_ROUTE) {
                composable(AksiNav.ASSESSMENT) { AssessmentWalkthroughScreen(navController) }
                composable(AksiNav.ASSESSMENT_STEP) { SelfAssessmentScreen(navController) }
                composable(
                    AksiNav.ASSESSMENT_RESULT + "/{${AksiNavArgument.ASSESSMENT_RESULT}}",
                    arguments = listOf(navArgument(AksiNavArgument.ASSESSMENT_RESULT) {
                        type = NavType.StringType
                    })
                ) { backStackEntry ->
                    val result = DataUtils.SelfAssessmentQuestion.AssessmentResult.entries.find {
                        it.name == backStackEntry.arguments?.getString(AksiNavArgument.ASSESSMENT_RESULT)
                    }
                    AssessmentResultScreen(
                        result ?: DataUtils.SelfAssessmentQuestion.AssessmentResult.NONE,
                        navController
                    )
                }
            }
        }
    }

}