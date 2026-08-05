package com.example.kintai;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// @Entity：このクラスがDBのテーブルと対応することをJPAに伝える
// アプリ起動時にJPAが自動で holidays テーブルを作成してくれる
@Entity
@Table(name = "holidays")
public class Holiday {

    @Id // 主キー（レコードを一意に識別する列）
    @GeneratedValue(strategy = GenerationType.IDENTITY) // idは自動採番（1, 2, 3...と自動で増える）
    private Long id;

    @Column(unique = true)
    private LocalDate date;      // 祝日の日（例: 2026-05-03）
    private String name;         // 祝日名（例: 憲法記念日）

    // --- getter（フィールドの値を取り出すメソッド）---
    public LocalDate getDate() { return date; }
    public String getName() { return name; }
}
