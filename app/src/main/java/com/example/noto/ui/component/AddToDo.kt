package com.example.noto.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.noto.ui.theme.*

@Composable
fun AddTodo(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    // 전체 행 구성
    Row(
        modifier = modifier
            .fillMaxWidth() // 가로 전체 채우기
            .height(IntrinsicSize.Min) // 최소 높이 지정
            .padding(
                start = SpacingUnit.spacing12,
                end = SpacingUnit.spacing16,
                top = SpacingUnit.spacing4,
                bottom = SpacingUnit.spacing4
            )
            .clickable { onClick() } // 클릭 시 이벤트 전달
            .background(GlobalColors.transparent), // 배경 투명 지정
        verticalAlignment = Alignment.CenterVertically, // 수직 정렬 중앙
        horizontalArrangement = Arrangement.spacedBy(SpacingUnit.spacing8) // 내부 요소 간격 지정
    ) {
        // 플러스 형태의 체크박스
        Checkbox(type = CheckboxType.Plus)

        // 텍스트 표시
        Text(
            text = "할일 추가하기",
            style = Typography.body1_medium,
            color = GlobalColors.black
        )
    }
}
