package com.example.kintai;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository を継承するだけで、SELECT・INSERT・UPDATE・DELETE が自動で使えるようになる
// <Attendance, Long> = 「Attendanceテーブルを操作する。主キーの型はLong」という意味
// interface なので実装は書かない。Spring Dataが自動で処理を生成してくれる
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // メソッド名のルールに従って書くだけで、SQLを自動生成してくれる
    // 「findBy + フィールド名 + And + フィールド名」→ WHERE user = ? AND date = ? に変換される
    // Optional は「値があるかもしれないし、ないかもしれない」という入れ物
    Optional<Attendance> findByUserAndDate(User user, LocalDate date);

    List<Attendance> findByUserAndDateBetween(User user, LocalDate start, LocalDate end);

}
