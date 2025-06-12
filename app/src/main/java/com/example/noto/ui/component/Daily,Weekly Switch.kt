package com.example.noto.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.noto.ui.theme.*

enum class SwitchType {
    DAILY,
    WEEKLY
}

@Composable
fun DailyWeeklySwitch(
    selected: SwitchType,
    onSelectedChange: (SwitchType) -> Unit,
    modifier: Modifier = Modifier,
) {
    // 외곽 박스 (배경 및 여백 포함)
    Box(
        modifier = modifier
            .padding(SpacingUnit.spacing4)
            .size(width = 168.dp, height = 40.dp)
            .background(
                color = BackgroundColors.disabled,
                shape = RoundedCornerShape(RadiusUnit.radius4)
            )
            .padding(SpacingUnit.spacing4)
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            // 일간 버튼
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(RadiusUnit.radius2))
                    .background(
                        if (selected == SwitchType.DAILY) GlobalColors.white else GlobalColors.transparent
                    )
                    .clickable { onSelectedChange(SwitchType.DAILY) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "일간",
                    style = Typography.label2_bold,
                    color = if (selected == SwitchType.DAILY) GlobalColors.black else TextColors.disabled
                )
            }

            // 주간 버튼
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(RadiusUnit.radius2))
                    .background(
                        if (selected == SwitchType.WEEKLY) GlobalColors.white else GlobalColors.transparent
                    )
                    .clickable { onSelectedChange(SwitchType.WEEKLY) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "주간",
                    style = Typography.label2_bold,
                    color = if (selected == SwitchType.WEEKLY) GlobalColors.black else TextColors.disabled
                )
            }
        }
    }
}
