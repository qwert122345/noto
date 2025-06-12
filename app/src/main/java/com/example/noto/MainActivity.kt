package com.example.noto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.toArgb
import com.example.noto.ui.theme.*
import com.example.noto.ui.screen.*
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 시스템 창 내 공간 확보 해제
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // 상태바, 내비게이션 바 색상 지정
        window.statusBarColor = GlobalColors.black.toArgb()
        window.navigationBarColor = GlobalColors.white.toArgb()

        setContent {
            // 현재 화면 상태 변수
            var screenState by remember { mutableStateOf(ScreenState.Home) }

            // 날짜별 할 일 목록 저장용 상태 변수
            val todoMap = remember { mutableStateMapOf<LocalDate, MutableList<String>>() }

            // 할 일 추가 함수 정의
            val onAddTodo: (LocalDate, String) -> Unit = { date, text ->
                val list = todoMap.getOrPut(date) { mutableListOf() }
                list.add(text)
            }

            // 화면 상태에 따라 해당 컴포저블 렌더링
            when (screenState) {
                ScreenState.Home -> MainHomeScreen(
                    onNavigateToAdd = { screenState = ScreenState.Adding_ToDo },
                    todoMap = todoMap,
                    onAddTodo = onAddTodo
                )

                ScreenState.Adding_ToDo -> AddingToDo(
                    onNavigateBack = { screenState = ScreenState.Home },
                    onAddTodo = onAddTodo
                )
            }

            // 시스템 UI 아이콘 밝기 설정
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.insetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                )
            } else {
                @Suppress("DEPRECATION")
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            }
        }
    }
}
