package com.example.hidesecretui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hidesecretui.ui.theme.HideSecretUiTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HideSecretUiTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets.safeContent
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        SecretAreaUseTapTrigger()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
// 累積按5下，觸發顯示或隱藏UI
fun SecretAreaUseTapTrigger() {
    var tapCount by remember { mutableIntStateOf(0) }
    var showSecret by remember { mutableStateOf(false) }
    var normalText by remember { mutableStateOf("") }
    var hiddenText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        tapCount++
                        if (tapCount >= 5) {
                            showSecret = !showSecret
                            tapCount = 0
                        }
                    }
                )
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 一般區塊
            TextField(
                value = normalText,
                onValueChange = { normalText = it },
                label = { Text("一般輸入") }
            )

            Button(onClick = { /* do something */ }) {
                Text("一般按鈕")
            }

            // 隱藏區塊（點五下後出現/消失）
            if (showSecret) {
                TextField(
                    value = hiddenText,
                    onValueChange = { hiddenText = it },
                    label = { Text("隱藏輸入") }
                )

                Button(onClick = { /* onSecretClick */ }) {
                    Text("隱藏按鈕")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
// 10內按5下，觸發顯示或隱藏UI
fun SecretAreaUseTapTriggerWithDelay() {
    var tapCount by remember { mutableIntStateOf(0) }
    var showSecret by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var timerJob by remember { mutableStateOf<Job?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        if (tapCount == 0) {
                            // 第一次點擊：啟動10秒計時器
                            timerJob?.cancel() // 清除舊計時器
                            timerJob = coroutineScope.launch {
                                delay(10_000L) // 等待10秒
                                tapCount = 0 // timeout未點滿則歸0
                            }
                        }

                        tapCount++

                        if (tapCount >= 5) {
                            showSecret = !showSecret
                            tapCount = 0
                            timerJob?.cancel() // 停止計時器
                        }
                    }
                )
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("一般輸入") }
            )

            Button(onClick = {}) {
                Text("一般按鈕")
            }

            if (showSecret) {
                TextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("隱藏輸入") }
                )

                Button(onClick = {}) {
                    Text("隱藏按鈕")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
// 按壓持續3秒，觸發顯示或隱藏UI
fun SecretAreaUseLongPressTrigger() {
    var showSecret by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var pressJob by remember { mutableStateOf<Job?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        pressJob?.cancel()

                        val job = coroutineScope.launch {
                            var released = false

                            val pressStart = System.currentTimeMillis()

                            // 啟動計時協程
                            val timerJob = launch {
                                delay(3000)
                                if (!released) {
                                    showSecret = !showSecret
                                }
                            }

                            // 等待使用者放開
                            tryAwaitRelease()
                            released = true

                            // 放開後，如果尚未觸發則取消 timer
                            if (System.currentTimeMillis() - pressStart < 3000) {
                                timerJob.cancel()
                            }
                        }

                        pressJob = job
                    }
                )
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(value = "", onValueChange = {}, label = { Text("一般輸入") })
            Button(onClick = {}) { Text("一般按鈕") }

            if (showSecret) {
                TextField(value = "", onValueChange = {}, label = { Text("隱藏輸入") })
                Button(onClick = {}) { Text("隱藏按鈕") }
            }
        }
    }
}

