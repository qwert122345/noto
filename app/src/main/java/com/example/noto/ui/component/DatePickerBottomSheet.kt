package com.example.noto.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example.noto.ui.theme.*
import com.example.noto.ui.theme.Typography
import androidx.compose.ui.res.painterResource
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerBottomSheet(
    visible: Boolean,
    onDismissRequest: () -> Unit,         // 바텀시트 닫기 이벤트
    selectedDate: LocalDate,              // 현재 선택된 날짜
    onDateSelected: (LocalDate) -> Unit,  // 날짜 선택 콜백
    onPrevWeek: () -> Unit,               // 이전 주 이동 콜백
    onNextWeek: () -> Unit,               // 다음 주 이동 콜백
    hasPrevWeek: Boolean,                 // 이전 주 이동 가능 여부
    hasNextWeek: Boolean                  // 다음 주 이동 가능 여부
) {
    if (!visible) return                  // visible이 false면 렌더링 생략

    val weekOfMonth = ((selectedDate.dayOfMonth - 1) / 7) + 1
    val title = "${selectedDate.monthValue}월 ${weekOfMonth}주차" // 주차 제목 표시
    val startOfWeek = selectedDate.minusDays(selectedDate.dayOfWeek.value.toLong() - 1)
    val dates = (0..6).map { startOfWeek.plusDays(it.toLong()) } // 한 주 날짜 리스트 생성

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        containerColor = GlobalColors.white,
        scrimColor = GlobalColors.black.copy(alpha = 0.32f),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp, vertical = SpacingUnit.spacing24)
        ) {
            // 상단 주차 제목과 주간 이동 버튼
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SpacingUnit.spacing16),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = Typography.body2_bold,
                    color = GlobalColors.black
                )

                Spacer(modifier = Modifier.weight(1f))

                // 이전 주 아이콘
                Icon(
                    painter = painterResource(id = com.example.noto.R.drawable.chevron_left),
                    contentDescription = "이전 주",
                    tint = if (hasPrevWeek) IconColors.default else IconColors.disabled,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable(enabled = hasPrevWeek) { onPrevWeek() }
                )

                Spacer(modifier = Modifier.width(SpacingUnit.spacing16))

                // 다음 주 아이콘
                Icon(
                    painter = painterResource(id = com.example.noto.R.drawable.chevron_right),
                    contentDescription = "다음 주",
                    tint = if (hasNextWeek) IconColors.default else IconColors.disabled,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable(enabled = hasNextWeek) { onNextWeek() }
                )
            }

            Spacer(modifier = Modifier.height(SpacingUnit.spacing16))

            // 한 주 날짜 목록 출력
            dates.forEach { date ->
                Text(
                    text = date.format(DateTimeFormatter.ofPattern("M월 d일 (E)", Locale.KOREAN)),
                    style = Typography.body1_medium,
                    color = GlobalColors.black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onDateSelected(date)
                            onDismissRequest()
                        }
                        .padding(vertical = SpacingUnit.spacing14, horizontal = SpacingUnit.spacing16)
                )
            }

            Spacer(modifier = Modifier.height(SpacingUnit.spacing16))
        }
    }
}
