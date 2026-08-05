package com.example.kintai;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// @Entity：このクラスがDBのテーブルと対応していることをJPAに伝えるアノテーション。
// アプリ起動時にJPAがこのクラスを読んで、自動でusersテーブルをDBに作成する。
@Entity

// @Table：対応するテーブル名を指定する。省略するとクラス名（user）がテーブル名になる。
@Table(name = "users")
public class User {

    // @Id：このフィールドがテーブルの主キー（PK）であることを示す。
    // @GeneratedValue：idの値を自動採番する（1, 2, 3...と自動で増える）。
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column：カラムの制約を設定する。
    // nullable=false → NOT NULL（必須）、unique=true → 重複不可、length=50 → 最大50文字。
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    // パスワードはBCryptでハッシュ化した文字列を保存するため、255文字まで許可している。
    @Column(nullable = false, length = 255)
    private String password;

    // getter：フィールドの値を外部から取得するためのメソッド。
    // UserDetailsServiceImpl.java がログイン処理でこれを呼び出す。
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
    return id;
    }

}
