package com.example.kintai;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;

// @Service をつけると、Spring がこのクラスをビジネスロジック層として管理する
// コントローラーからは new せずに使える（Spring が自動でインスタンスを作って渡してくれる）
@Service
public class AttendanceService {

    private final AttendanceMapper attendanceMapper;

    // コンストラクタインジェクション：Spring が AttendanceMapper を自動でセットしてくれる
    public AttendanceService(AttendanceMapper attendanceMapper) {
        this.attendanceMapper = attendanceMapper;
    }

    // 今日の打刻レコードを取得する。レコードがなければ null を返す
    public Attendance getTodayAttendance(User user) {
        LocalDate today = LocalDate.now();
        return attendanceMapper.findByUserIdAndDate(user.getId(), today);
    }

    // 出勤打刻：新しいAttendanceレコードを作ってDBに保存する
    public Attendance clockIn(User user) {
        LocalDate today = LocalDate.now();
        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setDate(today);
        attendance.setClockIn(roundTo15Minutes(LocalTime.now())); // 現在時刻を15分丸めしてセット
        attendanceMapper.insert(attendance);// DBにINSERT
        return attendance;
    }

    // 退勤打刻：今日のレコードを取得して clockOut を追加して保存する
    public void clockOut(User user) {
        LocalDate today = LocalDate.now();
        Attendance attendance = attendanceMapper.findByUserIdAndDate(user.getId(), today);
        attendance.setClockOut(roundTo15Minutes(LocalTime.now())); // 現在時刻を15分丸めしてセット
        attendanceMapper.updateClockOut(attendance); // DBにUPDATE
    }

    // 15分丸め処理（private = このクラス内だけで使う）
    // 例: 9:11 → 余り11。11 >= 8 なので切り上げ → 9:15
    // 例: 9:03 → 余り3。3 < 8 なので切り捨て → 9:00
    LocalTime roundTo15Minutes(LocalTime time) {
        int minute = time.getMinute();
        int remainder = minute % 15; // 15で割った余り（0〜14）
        if (remainder >= 8) {
            return time.plusMinutes(15 - remainder).withSecond(0).withNano(0); // 切り上げ
        } else {
            return time.minusMinutes(remainder).withSecond(0).withNano(0); // 切り捨て
        }
    }

    // 指定した年月の打刻データを全件取得する
    // LocalDate.of(year, month, 1) で月初、withDayOfMonth(lengthOfMonth()) で月末を作る
    public List<Attendance> getMonthAttendances(User user, int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        return attendanceMapper.findByUserIdAndDateBetween(user.getId(), start, end);
    }

    // 指定日のコメントを保存する（打刻レコードが存在しない場合は例外を投げる）
    public void saveComment(User user, LocalDate date, String comment) {
        Attendance attendance = attendanceMapper.findByUserIdAndDate(user.getId(), date);
        attendance.setComment(comment);
        attendanceMapper.updateComment(attendance);
    }

}
