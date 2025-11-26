package com.example.previewcomposetest

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.example.previewcomposetest.ui.theme.PreviewComposeTestTheme

// 預設 Preview
@Preview(showBackground = true, name = "Default")
@Composable
fun TestScreenPreviewDefault() {
    PreviewComposeTestTheme {
        TestScreen()
    }
}

// 顯示系統 UI 的範例
@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "System UI Preview"
)
@Composable
fun TestScreenPreviewSystemUi() {
    PreviewComposeTestTheme {
        TestScreen()
    }
}

// 繁體中文 (台灣) 預覽
@Preview(
    showBackground = true,
    locale = "zh-rTW",
    name = "Traditional Chinese (TW)"
)
@Composable
fun TestScreenPreviewZhTw() {
    PreviewComposeTestTheme {
        TestScreen()
    }
}

// 深色模式
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Composable
fun TestScreenPreviewDark() {
    PreviewComposeTestTheme {
        TestScreen()
    }
}

// 指定裝置大小 (Pixel 4)
@Preview(
    showBackground = true,
    device = Devices.PIXEL_4,
    name = "Pixel 4"
)
@Composable
fun TestScreenPreviewPixel4() {
    PreviewComposeTestTheme {
        TestScreen()
    }
}

// 自訂大小 (寬 320dp、高 640dp)
@Preview(
    showBackground = true,
    widthDp = 320,
    heightDp = 640,
    name = "Custom Size 320x640", device = "id:pixel_5"
)
@Composable
fun TestScreenPreviewCustomSize() {
    PreviewComposeTestTheme {
        TestScreen()
    }
}

// Pixel 9 自訂大小 (計算後的 dp 值)
@Preview(
    showBackground = true,
    widthDp = 412,
    heightDp = 924,
    name = "Pixel 9 Custom Size"
)
@Composable
fun TestScreenPreviewPixel9() {
    PreviewComposeTestTheme {
        TestScreen()
    }
}

// 指定裝置大小 (Pixel C 平板)
@Preview(
    showBackground = true,
    device = Devices.PIXEL_C,
    name = "Pixel C Tablet"
)
@Composable
fun TestScreenPreviewTablet() {
    PreviewComposeTestTheme {
        TestScreen()
    }
}