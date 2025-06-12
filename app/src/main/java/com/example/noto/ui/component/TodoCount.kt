package com.example.noto.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.noto.ui.theme.*

// 할 일 개수를 원형 배경 위에 표시하는 컴포저블
@Composable
fun TodoCount(
    label: String,                        // 표시할 숫자 텍스트
    modifier: Modifier = Modifier        // 외부에서 전달받는 modifier
) {
    // 배경 박스 정의
    Box(
        modifier = modifier
            .size(width = 40.dp, height = 28.dp) // 고정 크기 설정
            .clip(RoundedCornerShape(RadiusUnit.radius1000)) // 모서리 둥글게 처리
            .background(BackgroundColors.default),           // 배경색 지정
        contentAlignment = Alignment.Center                  // 자식 요소 중앙 정렬
    ) {
        // 텍스트가 비어 있지 않을 때만 출력
        if (label.isNotBlank()) {
            Text(
                text = label,                         // 표시할 숫자
                style = Typography.body2_medium,      // 텍스트 스타일
                color = GlobalColors.white,           // 텍스트 색상
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),             // 텍스트 수직 정렬
                textAlign = TextAlign.Center          // 텍스트 중앙 정렬
            )
        }
    }
}
