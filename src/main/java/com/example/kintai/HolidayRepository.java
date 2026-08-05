package com.example.kintai;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository を継承するだけで、SELECT・INSERT・UPDATE・DELETE が自動で使えるようになる
// <Holiday, Long> = 「Holidayテーブルを操作する。主キーの型はLong」という意味
public interface HolidayRepository extends JpaRepository<Holiday, Long> {

    // メソッド名のルールに従って書くだけで、SQLを自動生成してくれる
    // findByDateBetween → WHERE date BETWEEN ? AND ? に変換される
    // ユーザーに紐づかない（全ユーザー共通）ので User の条件は不要
    List<Holiday> findByDateBetween(LocalDate start, LocalDate end);

}
