package com.example.noto.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.noto.ui.theme.*
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

// 주간 할 일 목록을 날짜별 아코디언 형태로 표시하는 컴포저블
@Composable
fun WeeklyToDoList(
    dates: List<LocalDate>,                          // 주간 날짜 목록
    todoMap: Map<LocalDate, List<String>>,           // 날짜별 할 일 목록
    onDelete: (LocalDate, String) -> Unit            // 항목 삭제 콜백
) {
    // 날짜 수만큼 확장 상태를 기억하는 리스트 생성
    val expandedStates = remember { mutableStateListOf(*Array(dates.size) { false }) }

    Column {
        dates.forEachIndexed { index, date ->
            val todos = todoMap[date].orEmpty()                  // 해당 날짜의 할 일 목록
            if (todos.isEmpty()) return@forEachIndexed           // 할 일이 없으면 표시 생략

            val month = date.monthValue                          // 월 추출
            val day = date.dayOfMonth                            // 일 추출
            val dayOfWeek = date.dayOfWeek.getDisplayName(       // 요일 추출
                TextStyle.SHORT,
                Locale.KOREAN
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = if (expandedStates[index])
                    Arrangement.spacedBy(SpacingUnit.spacing4)
                else
                    Arrangement.Top
            ) {
                // 날짜 헤더 역할의 아코디언 표시
                DailyAccordion(
                    month = month,
                    day = day,
                    dayOfWeek = dayOfWeek,
                    isExpanded = expandedStates[index],
                    onClick = {
                        expandedStates[index] = !expandedStates[index] // 토글 처리
                    }
                )

                // 확장 상태일 경우 할 일 목록 표시
                if (expandedStates[index]) {
                    todos.forEach { todo ->
                        var isChecked by remember { mutableStateOf(false) }

                        DailyTodoItem(
                            text = todo,
                            isChecked = isChecked,
                            onCheckedChange = { isChecked = it },
                            onDelete = { onDelete(date, todo) } // 삭제 시 콜백 호출
                        )
                    }
                }
            }
        }
    }
}
