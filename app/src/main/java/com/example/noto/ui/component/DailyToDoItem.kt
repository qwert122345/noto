package com.example.noto.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import com.example.noto.R
import com.example.noto.ui.theme.*

@Composable
fun DailyTodoItem(
    text: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    // 할 일 항목 행 구성
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(
                start = SpacingUnit.spacing12,
                end = SpacingUnit.spacing16,
                top = SpacingUnit.spacing4,
                bottom = SpacingUnit.spacing4
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(SpacingUnit.spacing8)
    ) {
        // 체크박스 클릭 영역
        Box(modifier = Modifier.clickable { onCheckedChange(!isChecked) }) {
            Checkbox(type = if (isChecked) CheckboxType.Checked else CheckboxType.Default)
        }

        // 할 일 텍스트 표시
        Text(
            text = text,
            style = Typography.body1_medium,
            color = if (isChecked) TextColors.disabled else GlobalColors.black
        )

        // 오른쪽 여백 확보용
        Spacer(modifier = Modifier.weight(1f))

        // 삭제 아이콘
        Image(
            painter = painterResource(id = R.drawable.close),
            contentDescription = "삭제",
            modifier = Modifier
                .size(20.dp)
                .clickable { onDelete() },
            colorFilter = ColorFilter.tint(IconColors.disabled)
        )
    }
}
