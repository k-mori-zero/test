package com.example.kintai;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepositoryを継承するだけで、DBへの基本操作（SELECT・INSERT・UPDATE・DELETE）が
// 自動で使えるようになる。SQLを自分で書く必要がない。
// <User, Long> の意味：Userテーブルを操作し、主キーの型はLong（id）。
public interface UserRepository extends JpaRepository<User, Long> {

    // メソッド名のルールに従って書くだけで、JPAが自動でSQLを生成してくれる。
    // findByUsername(username) → SELECT * FROM users WHERE username = ? に変換される。
    // Optional：ユーザーが見つからない場合に null ではなく「空」を返すための型。
    Optional<User> findByUsername(String username);

}
