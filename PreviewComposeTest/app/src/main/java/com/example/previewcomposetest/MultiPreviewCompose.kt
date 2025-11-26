package com.example.previewcomposetest

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.example.previewcomposetest.ui.theme.PreviewComposeTestTheme

// @PreviewScreenSizes 是 Jetpack Compose 內建的 Multipreview Annotation
// 會同時產生多種常見螢幕尺寸的預覽 (手機、平板等)
@PreviewScreenSizes
@Composable
fun TestScreenBuildInMultiPreview() {
    PreviewComposeTestTheme {
        TestScreen()
    }
}

// 建立自訂的 Multipreview annotation
@Preview(
    name = "Default",
    showBackground = true)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(
    showBackground = true,
    locale = "zh-rTW",
    showSystemUi = true,
    name = "Traditional Chinese (TW)"
)
@Preview(
    showBackground = true,
    device = Devices.PIXEL_C,
    showSystemUi = true,
    name = "Pixel C Tablet"
)
annotation class MyMultiPreview

@MyMultiPreview
@Composable
fun TestScreenMyMultiPreview() {
    PreviewComposeTestTheme {
        TestScreen()
    }
}
