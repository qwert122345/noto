package com.example.noto.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import com.example.noto.R
import com.example.noto.ui.component.*
import com.example.noto.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*

enum class ScreenState {
    Home,
    Adding_ToDo
}

fun getTodayKoreanDate(): String {
    val today = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 EEEE", Locale.KOREAN)
    return today.format(formatter)
}

// 메인 홈 화면 컴포저블
@Composable
fun MainHomeScreen(
    onNavigateToAdd: () -> Unit,                      // 할 일 추가 화면 이동 콜백
    todoMap: Map<LocalDate, List<String>>,            // 날짜별 할 일 목록 맵
    onAddTodo: (LocalDate, String) -> Unit            // 할 일 추가 콜백
) {
    val todayText = getTodayKoreanDate()              // 오늘 날짜 텍스트
    var switchState by remember { mutableStateOf(SwitchType.DAILY) } // 보기 모드 상태
    var referenceDate by remember { mutableStateOf(LocalDate.now()) } // 기준 날짜

    // 상단 날짜 표시 텍스트
    val secondaryDateText = when (switchState) {
        SwitchType.DAILY -> referenceDate.format(DateTimeFormatter.ofPattern("M월 d일 (E)", Locale.KOREAN))
        SwitchType.WEEKLY -> {
            val weekOfMonth = referenceDate.get(WeekFields.of(Locale.KOREAN).weekOfMonth())
            "${referenceDate.monthValue}월 ${weekOfMonth}주차"
        }
    }

    // 기준 날짜 기준으로 주간 날짜 리스트 생성
    val startOfWeek = referenceDate.minusDays(referenceDate.dayOfWeek.value.toLong() - 1)
    val weeklyDates = (0..6).map { startOfWeek.plusDays(it.toLong()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // 상단 영역: 오늘 날짜 + 주간 요약
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(GlobalColors.black)
                .padding(horizontal = SpacingUnit.spacing16)
                .padding(top = SpacingUnit.spacing56, bottom = SpacingUnit.spacing16)
        ) {
            Text(
                text = todayText,
                style = Typography.label2_bold,
                color = GlobalColors.white,
                modifier = Modifier.padding(bottom = SpacingUnit.spacing16)
            )

            OneWeek(
                selectedDate = referenceDate,
                onDateSelected = { referenceDate = it },
                todoCounts = weeklyDates.associateWith { date -> todoMap[date]?.size ?: 0 }
            )
        }

        // 날짜 표시 + 이전/다음 이동 + 보기 전환 스위치
        Spacer(modifier = Modifier.height(SpacingUnit.spacing12))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SpacingUnit.spacing16),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.chevron_left),
                contentDescription = "이전",
                colorFilter = ColorFilter.tint(IconColors.default),
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = SpacingUnit.spacing4)
                    .clickable {
                        referenceDate = when (switchState) {
                            SwitchType.DAILY -> referenceDate.minusDays(1)
                            SwitchType.WEEKLY -> referenceDate.minusWeeks(1)
                        }
                    }
            )

            Text(
                text = secondaryDateText,
                style = Typography.body2_medium,
                color = GlobalColors.black
            )

            Image(
                painter = painterResource(id = R.drawable.chevron_right),
                contentDescription = "다음",
                colorFilter = ColorFilter.tint(IconColors.default),
                modifier = Modifier
                    .size(24.dp)
                    .padding(start = SpacingUnit.spacing4)
                    .clickable {
                        referenceDate = when (switchState) {
                            SwitchType.DAILY -> referenceDate.plusDays(1)
                            SwitchType.WEEKLY -> referenceDate.plusWeeks(1)
                        }
                    }
            )

            Spacer(modifier = Modifier.weight(1f))

            DailyWeeklySwitch(
                selected = switchState,
                onSelectedChange = { switchState = it },
            )
        }

        // 할 일 추가 버튼
        Spacer(modifier = Modifier.height(SpacingUnit.spacing12))
        AddTodo(onClick = onNavigateToAdd)

        // 할 일 리스트 출력
        Spacer(modifier = Modifier.height(SpacingUnit.spacing12))

        if (switchState == SwitchType.DAILY) {
            // 일간 보기
            todoMap[referenceDate]?.forEach { todo ->
                var isChecked by remember { mutableStateOf(false) }

                DailyTodoItem(
                    text = todo,
                    isChecked = isChecked,
                    onCheckedChange = { isChecked = it },
                    onDelete = {
                        val list = todoMap[referenceDate]?.toMutableList()
                        list?.remove(todo)
                        if (list != null) {
                            (todoMap as MutableMap)[referenceDate] = list
                        }
                    }
                )
            }
        } else {
            // 주간 보기
            WeeklyToDoList(
                dates = weeklyDates,
                todoMap = todoMap,
                onDelete = { date, todo ->
                    val list = todoMap[date]?.toMutableList()
                    list?.remove(todo)
                    if (list != null) {
                        (todoMap as MutableMap)[date] = list
                    }
                }
            )
        }
    }
}
