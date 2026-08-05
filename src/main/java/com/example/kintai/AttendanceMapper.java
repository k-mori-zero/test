package com.example.kintai;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

// @Mapper をつけると、Spring が起動時にこのインターフェースの実装クラスを自動生成してくれる
// SQL は AttendanceMapper.xml に書く。このインターフェースとXMLがメソッド名で紐づく
@Mapper
public interface AttendanceMapper {
    // 指定ユーザー・指定日の打刻レコードを1件取得する。レコードがなければ null を返す
    Attendance findByUserIdAndDate(Long userId, LocalDate date);
    // 新しい打刻レコードをDBにINSERTする
    void insert(Attendance attendance);
    // 退勤時刻（clockOut）をDBにUPDATEする
    void updateClockOut(Attendance attendance);
    // 指定ユーザー・指定期間の打刻レコードを全件取得する
    List<Attendance> findByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end);
    // コメントをDBにUPDATEする
    void updateComment(Attendance attendance);
}
