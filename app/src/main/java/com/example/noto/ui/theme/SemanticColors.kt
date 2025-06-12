package com.example.noto.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf

object GlobalColors {
    val black = Black
    val white = White
    val transparent = Transparent
}

object BackgroundColors {
    val disabled = Gray20
    val default = Gray60
}

object BorderColors {
    val default = Gray20
}

object TextColors {
    val disabled = Gray60
    val disabledInverse = Gray20
    val assistInverse = Gray40
}

object IconColors {
    val default = Gray90
    val disabled = Gray40
    val inverse = Gray20
}

object StatusColors {
    val success = Yellow50
    val error = Red40
}

val LocalSemanticColors = staticCompositionLocalOf<Unit> {
    error("No SemanticColors provided")
}