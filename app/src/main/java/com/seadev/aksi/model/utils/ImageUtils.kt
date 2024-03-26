package com.seadev.aksi.model.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.seadev.aksi.model.BuildConfig

data class RemoteImage(
    val day: String,
    val night: String,
)

object ImageUtils {
    private val LOGO_ITTP = RemoteImage(
        BuildConfig.RESOURCE_URL + "f484ba76-96ed-46b2-9c87-d18f17bf33c8",
        BuildConfig.RESOURCE_URL + "8d6a1e33-4ab1-46e3-ad2e-76c8a49f6f1d"
    )
    private val LOGO_AKSI = RemoteImage(
        BuildConfig.RESOURCE_URL + "8803fa06-5546-4fa0-80f1-cadfe5d11265",
        BuildConfig.RESOURCE_URL + "9f455acc-ac59-4a72-9575-4773a8e549a4"
    )
    private val ICON_DISTRIBUTION = RemoteImage(
        BuildConfig.RESOURCE_URL + "a2998a00-6290-46a4-a4de-73855cc51aff",
        BuildConfig.RESOURCE_URL + "c4803b63-cb3e-4010-9ff8-28520b847866"
    )
    private val ICON_ASSESSMENT = RemoteImage(
        BuildConfig.RESOURCE_URL + "9db06e4d-6f1e-4cfa-b4a9-437ab84bba90",
        BuildConfig.RESOURCE_URL + "ac24b34a-7eed-49d6-8c15-18531960bbe4",
    )
    private val ICON_HOTLINE = RemoteImage(
        BuildConfig.RESOURCE_URL + "aef429f8-9b1e-462a-ac0a-b33ea06956aa",
        BuildConfig.RESOURCE_URL + "2dae94d7-92d5-40ec-9d33-05edb82aad1b",
    )
    private val ICON_PREVENTION = RemoteImage(
        BuildConfig.RESOURCE_URL + "08e565ed-0436-419c-8209-37c641207232",
        BuildConfig.RESOURCE_URL + "90b23f6d-497a-47c9-8df6-a044819a75b1",
    )
    private val ICON_PROFILE = RemoteImage(
        BuildConfig.RESOURCE_URL + "9b7521b1-494a-4770-8ec7-ca68b598fc33",
        BuildConfig.RESOURCE_URL + "de4dd25b-fb1a-453c-91c9-923e156a6e7a",
    )
    private val IMAGE_CAMPAIGN = RemoteImage(
        BuildConfig.RESOURCE_URL + "c77f4006-9162-4cc0-90e4-486714d40f01",
        BuildConfig.RESOURCE_URL + "e210a64f-fcc1-43f6-a49a-18ea918546e1",
    )
    val LogoITTP: String @Composable get() = if (isSystemInDarkTheme()) LOGO_ITTP.night else LOGO_ITTP.day
    val LogoAksi: String @Composable get() = if (isSystemInDarkTheme()) LOGO_AKSI.night else LOGO_AKSI.day
    val IconDistribution: String @Composable get() = if (isSystemInDarkTheme()) ICON_DISTRIBUTION.night else ICON_DISTRIBUTION.day
    val IconAssessment: String @Composable get() = if (isSystemInDarkTheme()) ICON_ASSESSMENT.night else ICON_ASSESSMENT.day
    val IconHotline: String @Composable get() = if (isSystemInDarkTheme()) ICON_HOTLINE.night else ICON_HOTLINE.day
    val IconProfile: String @Composable get() = if (isSystemInDarkTheme()) ICON_PROFILE.night else ICON_PROFILE.day
    val IconPrevention: String @Composable get() = if (isSystemInDarkTheme()) ICON_PREVENTION.night else ICON_PREVENTION.day
    val IlCampaign: String @Composable get() = if (isSystemInDarkTheme()) IMAGE_CAMPAIGN.night else IMAGE_CAMPAIGN.day
    const val IlSelfAssessment = BuildConfig.RESOURCE_URL + "ad939596-a8a6-4e92-a044-1ddb1dbcbbfa"
    const val IconGoogle = BuildConfig.RESOURCE_URL + "c6f73145-f7fa-4d35-b872-467f97f75368"
    object AssessmentResult{
        const val IconLowRisk = BuildConfig.RESOURCE_URL + "613be480-81de-494b-b502-2c76e58e50b6"
        const val IconMidRisk = BuildConfig.RESOURCE_URL + "87963612-a9b2-453a-86cf-5bdc963375aa"
        const val IconHighRisk = BuildConfig.RESOURCE_URL + "032179d9-4563-46cb-a425-1bd2a45ea269"
    }
    object NavProfileIcon{
        val IconNext = BuildConfig.RESOURCE_URL + "f83a4e83-3a7c-4ede-9054-af3b8e8e4dcd"
        val IconLocation = BuildConfig.RESOURCE_URL + "83ff52cf-a328-41a0-93ef-0a5ce4782149"
        val IconLogOut = BuildConfig.RESOURCE_URL + "9ffc4809-793a-4b48-8b0f-4a53bd060a0d"
        val IconEmail = BuildConfig.RESOURCE_URL + "656e4fba-4ae4-48f4-be0b-6e804c5efb7f"
        val IconCall = BuildConfig.RESOURCE_URL + "48341d0b-4927-4de8-9c33-2ae87c3125b0"
        val IconFeedBack = BuildConfig.RESOURCE_URL + "8620fd04-8eca-49cc-a70c-f16658085a6d"
    }
}