package com.seadev.aksi.model.ui.screen.assessment

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.seadev.aksi.model.ui.component.assessment.AksiDialog
import com.seadev.aksi.model.ui.component.assessment.AssessmentStep
import com.seadev.aksi.model.ui.component.assessment.AssessmentTitle
import com.seadev.aksi.model.ui.theme.AksiColor
import com.seadev.aksi.model.ui.theme.AksiKampungSiagaCovidTheme
import com.seadev.aksi.model.utils.AksiNav
import com.seadev.aksi.model.utils.DataUtils
import com.seadev.aksi.model.utils.DataUtils.SelfAssessmentQuestion.getAssessmentResult
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SelfAssessmentScreen(
    navController: NavController,
    assessmentViewModel: AssessmentViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var onIndexChange by remember { mutableIntStateOf(0) }
    var isButtonEnable by remember { mutableStateOf(true) }
    var resultRespond by remember {
        mutableStateOf(DataUtils.SelfAssessmentQuestion.initialRespond)
    }
    var showDialog by remember { mutableStateOf(false) }
    val listQuestion by remember {
        mutableStateOf(
            DataUtils.SelfAssessmentQuestion.step1 +
                    DataUtils.SelfAssessmentQuestion.step2 +
                    DataUtils.SelfAssessmentQuestion.step3
        )
    }
    val pagerState = rememberPagerState { listQuestion.size }
    var isAssessmentFinish by remember { mutableStateOf(false) }

    if (showDialog) AksiDialog(
        dialogText = "Apakah ingin keluar dari penilaian diri dan hapus jawaban?",
        dialogTitle = "Keluar dari Penilaian Diri",
        icon = Icons.Rounded.Delete,
        onDismissRequest = { showDialog = false },
        onConfirmation = navController::popBackStack
    )
    Scaffold(topBar = {
        TopAppBar(navigationIcon = {
            IconButton(onClick = {
                coroutineScope.launch {
                    if (listQuestion[pagerState.currentPage].desc.isBlank())
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    else showDialog = true
                }
            }) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Back")
            }
        }, title = {
            Text(
                modifier = Modifier,
                text = "",
                fontWeight = FontWeight.Bold,
                color = if (isSystemInDarkTheme()) AksiColor.Text else AksiColor.Primary
            )
        })
    }) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val (contentRef, btnStart) = createRefs()

            LaunchedEffect(pagerState.currentPage) {
                if (getValidIndex(pagerState.currentPage) != -1) isButtonEnable =
                    resultRespond[getValidIndex(pagerState.currentPage)] != -1
            }
            if (isAssessmentFinish) {
                val result = resultRespond.getAssessmentResult()
                FinishAssessmentContent(
                    modifier = Modifier.constrainAs(contentRef){
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                ){
                    assessmentViewModel.addHistoryAssessment(result) { isSuccess ->
                        if (isSuccess) navController.navigate(AksiNav.ASSESSMENT_RESULT + "/${result.name}")
                        else Toast.makeText(
                            context, "Gagal menyimpan hasil penilaian diri", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else HorizontalPager(
                modifier = Modifier.constrainAs(contentRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(btnStart.top, 16.dp)
                }, state = pagerState,
                userScrollEnabled = false
            ) { index ->
                if (listQuestion[index].desc.isNotBlank()) AssessmentTitle(stepTitle = listQuestion[index])
                else AssessmentStep(
                    resultRespond[getValidIndex(index)],
                    listQuestion[index]
                ) { selectedIndex ->
                    onIndexChange = selectedIndex
                    resultRespond = resultRespond.toMutableList().apply {
                        this[getValidIndex(index)] = selectedIndex
                    }
                    isButtonEnable = true
                }
            }
            if (!isAssessmentFinish) Button(
                onClick = {
                    if (pagerState.currentPage != listQuestion.size - 1) coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    } else {
                        isAssessmentFinish = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AksiColor.Secondary
                ),
                enabled = isButtonEnable,
                modifier = Modifier.constrainAs(btnStart) {
                    bottom.linkTo(parent.bottom, 16.dp)
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    width = Dimension.fillToConstraints
                }) {
                Text(
                    text = if (listQuestion[pagerState.currentPage].desc.isNotBlank()) "Mulai" else "Selanjutnya",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

fun getValidIndex(index: Int) = when (index) {
    in 0..10 -> index - 1
    in 11..17 -> index - 2
    in 18..23 -> index - 3
    else -> -1
}

@Composable
private fun FinishAssessmentContent(
    modifier: Modifier = Modifier,
    onFinish: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.5f))
        Icon(
            modifier = Modifier.size(200.dp),
            imageVector = Icons.Rounded.CheckCircle, contentDescription = "",
            tint = AksiColor.Primary
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Selesai",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = AksiColor.Text
        )
        Text(
            text = "Terima kasih telah mengisi penilaian diri harian",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = AksiColor.TextLight,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onFinish,
            colors = ButtonDefaults.buttonColors(
                containerColor = AksiColor.Secondary
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Lihat Hasil",
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun PreviewFinishAssessmentContent() {
    AksiKampungSiagaCovidTheme {
        FinishAssessmentContent{
        }
    }
}

@Preview
@Composable
private fun PreviewSelfAssessmentScreen() {
    AksiKampungSiagaCovidTheme {
        SelfAssessmentScreen(rememberNavController())
    }
}