package com.example.cyb
//19018095최윤빈
import android.os.Bundle
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar


class MainActivity : AppCompatActivity() {

    private lateinit var cybCalendarView: CalendarView
    private var cybRemainingDaysToast: Toast? = null // 토스트 메세지 객체 저장 변수


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cybCalendarView = findViewById(R.id.cybcalendarView)

        cybCalendarView.setOnDateChangeListener { _, cybyear, cybmonth, cybday ->   //날짜 선택시 발생하는 리스너
            val cybselectedDate = Calendar.getInstance()
            cybselectedDate.set(cybyear, cybmonth, cybday) // 선택한 날짜 저장
            val cybtoday = Calendar.getInstance() // 현재 날짜 저장
            val cybremainingDays = cybCalculateRemainingDays(cybtoday, cybselectedDate) // 선택한 날짜까지의 남은 일수 계산

            // 기존의 토스트 메세지 객체가 있다면 취소
            cybRemainingDaysToast?.cancel()
            // 새로운 토스트 메세지 객체 생성 및 표시
            cybRemainingDaysToast =
                Toast.makeText(this, "선택한 날짜까지 ${cybremainingDays}일 남았습니다.", Toast.LENGTH_SHORT)
            cybRemainingDaysToast?.show()
        }
    }

    private fun cybCalculateRemainingDays(cybStartDate: Calendar, cybEndDate: Calendar): Int {
        // 현재 시간을 선택한 날짜의 자정으로 설정
        cybStartDate.set(Calendar.HOUR_OF_DAY, 0)
        cybStartDate.set(Calendar.MINUTE, 0)
        cybStartDate.set(Calendar.SECOND, 0)
        cybStartDate.set(Calendar.MILLISECOND, 0)

        val cybStartTimeInMillis = cybStartDate.timeInMillis // 현재 날짜 시간 밀리초로 반환
        val cybEndTimeInMillis = cybEndDate.timeInMillis // 선택한 날짜 시간 밀리초로 반환
        val cybDifferenceInMillis = cybEndTimeInMillis - cybStartTimeInMillis //두 일간의 시간 차 계산
        val cybDifferenceInDays = cybDifferenceInMillis / (24 * 60 * 60 * 1000) //하루를 나타내는 밀리초 수로 나눔

        if (cybDifferenceInMillis < 0) {
            return cybDifferenceInDays.toInt() - 1 // 선택한 날짜가 과거인 경우 1일을 뺀 값을 반환
        }
        return cybDifferenceInDays.toInt() // 선택한 날짜가 미래인 경우 그대로 반환
    }
}
