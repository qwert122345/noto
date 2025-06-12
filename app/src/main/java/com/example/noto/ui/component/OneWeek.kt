package com.example.noto.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.noto.ui.theme.*
import java.time.LocalDate

@Composable
fun OneWeek(
    selectedDate: LocalDate,                 // 선택된 날짜
    onDateSelected: (LocalDate) -> Unit,     // 날짜 선택 시 동작 콜백
    todoCounts: Map<LocalDate, Int>          // 날짜별 할 일 개수 맵
) {
    val days = listOf("월", "화", "수", "목", "금", "토", "일") // 요일 리스트
    val today = LocalDate.now()                                   // 오늘 날짜
    val startOfWeek = selectedDate.minusDays((selectedDate.dayOfWeek.value - 1).toLong()) // 주 시작일 계산

    // 초기 날짜 정보 리스트 생성 (라벨, 유형, 날짜)
    val initialDates = (0..6).map { i ->
        val date = startOfWeek.plusDays(i.toLong())
        val label = date.dayOfMonth.toString()
        val type = when {
            date == today -> DateType.TODAY
            date == selectedDate -> DateType.SELECTED
            else -> DateType.DEFAULT
        }
        Triple(label, type, date)
    }

    // 상태로 날짜 리스트 저장
    val dates = remember(selectedDate) {
        mutableStateListOf(*initialDates.toTypedArray())
    }

    // 한 주간 날짜 표시 Row
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(SpacingUnit.spacing16)
    ) {
        days.indices.forEach { i ->
            val (label, type, date) = dates[i]
            val count = todoCounts[date] ?: 0
            val countLabel = if (count > 0) count.toString() else "" // 할 일이 없으면 텍스트 생략

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // 요일 텍스트
                Text(
                    text = days[i],
                    style = Typography.label2_medium,
                    color = TextColors.disabledInverse,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(SpacingUnit.spacing8))

                // 할 일 개수 (텍스트만 조건부 출력)
                TodoCount(label = countLabel)

                Spacer(modifier = Modifier.height(SpacingUnit.spacing6))

                // 날짜 버튼
                Box(
                    modifier = Modifier.clickable {
                        for (j in dates.indices) {
                            val (oldLabel, _, oldDate) = dates[j]
                            val newType = when {
                                oldDate == today -> DateType.TODAY
                                j == i -> if (oldDate != today) DateType.SELECTED else DateType.TODAY
                                else -> DateType.DEFAULT
                            }
                            dates[j] = Triple(oldLabel, newType, oldDate)
                        }
                        onDateSelected(date)
                    }
                ) {
                    Date(label = label, type = type)
                }
            }
        }
    }
}
