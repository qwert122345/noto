package com.example.noto.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.noto.ui.theme.*

// 날짜 유형 구분용 enum 클래스
enum class DateType {
    DEFAULT,   // 기본 상태
    SELECTED,  // 선택된 상태
    TODAY      // 오늘 날짜
}

@Composable
fun Date(
    label: String,
    type: DateType,
    modifier: Modifier = Modifier
) {
    // 유형별 배경색 설정
    val backgroundColor = when (type) {
        DateType.DEFAULT -> GlobalColors.transparent
        DateType.SELECTED -> BackgroundColors.default
        DateType.TODAY -> GlobalColors.white
    }

    // 유형별 텍스트 색상 설정
    val textColor = when (type) {
        DateType.DEFAULT -> TextColors.assistInverse
        DateType.SELECTED -> GlobalColors.white
        DateType.TODAY -> GlobalColors.black
    }

    // 날짜 박스 UI 구성
    Box(
        modifier = modifier
            .size(24.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(RadiusUnit.radius1000)
            ),
        contentAlignment = Alignment.Center
    ) {
        // 날짜 텍스트 표시
        Text(
            text = label,
            style = if (type == DateType.TODAY) Typography.label2_bold else Typography.label2_medium,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}
