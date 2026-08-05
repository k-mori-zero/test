package com.example.kintai;

import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// @Entity をつけると、このクラスがDBのテーブルと対応することをJPAに伝える
// アプリ起動時にJPAが自動で attendances テーブルを作成してくれる
@Entity
@Table(name = "attendances") // テーブル名を "attendances" に指定
public class Attendance {

    @Id // 主キー（レコードを一意に識別する列）
    @GeneratedValue(strategy = GenerationType.IDENTITY) // idは自動採番（1, 2, 3...と自動で増える）
    private Long id;

    // @ManyToOne：「多対1」の関係。打刻レコードは複数あるが、対応するユーザーは1人
    // @JoinColumn：DBに "user_id" カラムを作り、usersテーブルのidと紐づける（外部キー）
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false) // NULLを許可しない（必ず日付が入る）
    private LocalDate date;   // 打刻日（例: 2026-06-17）
    private LocalTime clockIn;  // 出勤時刻（未打刻はnull）
    private LocalTime clockOut; // 退勤時刻（未打刻はnull）
    private String comment; // コメント（任意）

    // --- setter（フィールドに値をセットするメソッド）---
    public void setUser(User user) { this.user = user; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setClockIn(LocalTime clockIn) { this.clockIn = clockIn; }
    public void setClockOut(LocalTime clockOut) { this.clockOut = clockOut; }
    public void setComment(String comment) { this.comment = comment; }

    // --- getter（フィールドの値を取り出すメソッド）---
    public User getUser() { return user; }
    public LocalDate getDate() { return date; }
    public LocalTime getClockIn() { return clockIn; }
    public LocalTime getClockOut() { return clockOut; }
    public String getComment() { return comment; }

}
