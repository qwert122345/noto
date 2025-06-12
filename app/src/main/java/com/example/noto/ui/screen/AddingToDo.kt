package com.example.noto.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import com.example.noto.R
import com.example.noto.ui.component.DatePickerBottomSheet
import com.example.noto.ui.theme.*
import java.time.LocalDate

// 할 일 추가 화면 컴포저블
@Composable
fun AddingToDo(
    onNavigateBack: () -> Unit,                            // 뒤로가기 콜백
    onAddTodo: (LocalDate, String) -> Unit                 // 할 일 추가 콜백
) {
    var inputText by remember { mutableStateOf("") }       // 입력 텍스트 상태
    var dateText by remember { mutableStateOf("") }        // 선택된 날짜 텍스트 상태
    var showDatePicker by remember { mutableStateOf(false) } // 바텀시트 표시 여부
    var selectedDate by remember { mutableStateOf(LocalDate.now()) } // 선택된 날짜

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    // 진입 시 키보드 자동 표시
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GlobalColors.white)
    ) {
        // 상단 AppBar 영역
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(GlobalColors.black)
                .padding(
                    top = SpacingUnit.spacing72,
                    bottom = SpacingUnit.spacing16,
                    start = SpacingUnit.spacing16,
                    end = SpacingUnit.spacing16
                )
        ) {
            Text(
                text = "할일 추가하기",
                style = Typography.body1_medium,
                color = GlobalColors.white,
                modifier = Modifier.align(Alignment.Center)
            )

            androidx.compose.foundation.Image(
                painter = painterResource(id = R.drawable.chevron_left),
                contentDescription = "뒤로가기",
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterStart)
                    .clickable { onNavigateBack() }
            )
        }

        Spacer(modifier = Modifier.height(SpacingUnit.spacing24))

        // 할 일 입력 필드
        InputField(
            text = inputText,
            onTextChange = { inputText = it },
            placeholder = "추가할 할일을 써주세요",
            modifier = Modifier.focusRequester(focusRequester)
        )

        // 날짜 선택 필드
        if (inputText.isNotBlank()) {
            InputField(
                text = dateText,
                onTextChange = {},
                placeholder = "할일을 추가할 날짜를 선택해주세요",
                readOnly = true,
                onClick = {
                    focusManager.clearFocus()
                    showDatePicker = true
                }
            )
        }

        // 추가하기 버튼
        if (inputText.isNotBlank() && dateText.isNotBlank()) {
            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = SpacingUnit.spacing32)
                    .navigationBarsPadding()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                Box(
                    modifier = Modifier
                        .width(379.dp)
                        .height(56.dp)
                        .background(
                            color = GlobalColors.black,
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            onAddTodo(selectedDate, inputText)
                            onNavigateBack()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "추가하기",
                        style = Typography.body1_bold,
                        color = GlobalColors.white
                    )
                }
            }
        }

        // 날짜 선택 바텀시트
        if (showDatePicker) {
            DatePickerBottomSheet(
                visible = true,
                selectedDate = selectedDate,
                onDismissRequest = { showDatePicker = false },
                onDateSelected = {
                    dateText = it.format(java.time.format.DateTimeFormatter.ofPattern("M월 d일"))
                    selectedDate = it
                    showDatePicker = false
                },
                onPrevWeek = { selectedDate = selectedDate.minusWeeks(1) },
                onNextWeek = { selectedDate = selectedDate.plusWeeks(1) },
                hasPrevWeek = true,
                hasNextWeek = true
            )
        }
    }
}

// 입력 필드 공용 컴포저블
@Composable
fun InputField(
    text: String,                                      // 입력 텍스트
    onTextChange: (String) -> Unit,                    // 텍스트 변경 콜백
    placeholder: String,                               // 플레이스홀더 텍스트
    readOnly: Boolean = false,                         // 읽기 전용 여부
    onClick: (() -> Unit)? = null,                     // 클릭 콜백 (날짜 선택용)
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SpacingUnit.spacing16)
            .background(GlobalColors.white)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
    ) {
        BasicTextField(
            value = text,
            onValueChange = onTextChange,
            readOnly = readOnly,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = SpacingUnit.spacing16),
            textStyle = Typography.body3_medium.copy(color = GlobalColors.black),
            cursorBrush = SolidColor(GlobalColors.black),
            keyboardOptions = KeyboardOptions(
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            decorationBox = { innerTextField ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (text.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = Typography.body3_medium,
                                color = TextColors.disabled
                            )
                        }
                        innerTextField()
                    }

                    Spacer(modifier = Modifier.height(SpacingUnit.spacing12))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(
                                if (text.isNotEmpty()) GlobalColors.black
                                else BorderColors.default
                            )
                    )
                }
            }
        )
    }
}
