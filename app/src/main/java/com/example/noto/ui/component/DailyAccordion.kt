package com.example.noto.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.noto.ui.theme.Typography
import com.example.noto.ui.theme.*
import com.example.noto.R

@Composable
fun DailyAccordion(
    isExpanded: Boolean,
    month: Int,
    day: Int,
    dayOfWeek: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    // 전체 영역 클릭 가능 박스
    Row(
        modifier = modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .height(48.dp)
            .padding(
                start = SpacingUnit.spacing16,
                end = SpacingUnit.spacing16,
                top = SpacingUnit.spacing12,
                bottom = SpacingUnit.spacing8
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // 날짜 텍스트 영역
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${month}월",
                style = Typography.body1_medium,
                color = GlobalColors.black
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = "${day}일",
                style = Typography.body1_medium,
                color = GlobalColors.black
            )
            Text(
                text = "($dayOfWeek)",
                style = Typography.body1_medium,
                color = GlobalColors.black
            )
        }

        // 확장 여부에 따른 아이콘 표시
        Icon(
            painter = painterResource(id = if (isExpanded) R.drawable.chevron_up else R.drawable.chevron_under),
            contentDescription = null,
            tint = IconColors.disabled,
            modifier = Modifier.size(20.dp)
        )
    }
}
