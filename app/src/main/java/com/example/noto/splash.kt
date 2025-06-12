package com.example.noto

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.noto.ui.theme.*

class splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 엣지 투 엣지 레이아웃 활성화
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        // 상태바, 내비게이션 바 색상 지정
        window.statusBarColor = GlobalColors.black.toArgb()
        window.navigationBarColor = GlobalColors.black.toArgb()

        // 로고 텍스트 뷰 초기화
        val logoTextView = findViewById<TextView>(R.id.logotext)
        val logo = "noto"

        // 초기 로고 색상: 전체 흰색
        val whiteSpan = SpannableString(logo).apply {
            setSpan(
                ForegroundColorSpan(GlobalColors.white.toArgb()),
                0, length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        logoTextView.text = whiteSpan

        // 1.6초 후 로고 일부 색상 변경
        Handler(Looper.getMainLooper()).postDelayed({
            val coloredSpan = SpannableString(logo).apply {
                setSpan(ForegroundColorSpan(GlobalColors.white.toArgb()), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(ForegroundColorSpan(StatusColors.success.toArgb()), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(ForegroundColorSpan(GlobalColors.white.toArgb()), 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(ForegroundColorSpan(StatusColors.success.toArgb()), 3, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            logoTextView.text = coloredSpan
        }, 1600)

        // 상태바 및 내비게이션 바 여백 반영
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.splash)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 3초 후 MainActivity로 전환
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)
    }
}
