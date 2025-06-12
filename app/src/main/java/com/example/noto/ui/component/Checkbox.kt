package com.example.noto.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.noto.ui.theme.*
import com.example.noto.R

// 체크박스 타입 정의
enum class CheckboxType {
    Default,    // 회색 배경, 아이콘 없음
    Checked,    // 검정 배경, 체크 아이콘 표시
    Plus        // 흰색 배경, 플러스 아이콘, 테두리 포함
}

@Composable
fun Checkbox(
    type: CheckboxType,
    modifier: Modifier = Modifier
) {
    // 배경색 설정
    val backgroundColor: Color

    // 아이콘 리소스 ID, 색상, 크기 변수
    var iconResId: Int? = null
    var iconColor: Color = Color.Unspecified
    var iconSize: Pair<Int, Int>? = null

    // 타입별 시각 속성 설정
    when (type) {
        CheckboxType.Default -> {
            backgroundColor = BackgroundColors.disabled
        }
        CheckboxType.Checked -> {
            backgroundColor = GlobalColors.black
            iconResId = R.drawable.check
            iconColor = GlobalColors.white
            iconSize = 14 to 10
        }
        CheckboxType.Plus -> {
            backgroundColor = GlobalColors.white
            iconResId = R.drawable.plus
            iconColor = GlobalColors.black
            iconSize = 12 to 12
        }
    }

    // 외곽 박스 (간격 포함)
    Box(
        modifier = modifier
            .width(40.dp)
            .padding(
                horizontal = SpacingUnit.spacing4,
                vertical = SpacingUnit.spacing8
            ),
        contentAlignment = Alignment.Center
    ) {
        // 실제 체크박스 영역
        Box(
            modifier = Modifier
                .width(32.dp)
                .height(24.dp)
                .let {
                    // Plus 타입일 경우 테두리 적용
                    if (type == CheckboxType.Plus) {
                        it.border(
                            width = 2.dp,
                            color = GlobalColors.black,
                            shape = RoundedCornerShape(RadiusUnit.radius1000)
                        )
                    } else it
                }
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(RadiusUnit.radius1000)
                ),
            contentAlignment = Alignment.Center
        ) {
            // 아이콘이 있을 경우 표시
            iconResId?.let { resId ->
                iconSize?.let { (w, h) ->
                    Icon(
                        painter = painterResource(id = resId),
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(width = w.dp, height = h.dp)
                    )
                }
            }
        }
    }
}
