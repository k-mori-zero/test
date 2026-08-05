package com.example.kintai;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// @Service：このクラスがサービス層（ビジネスロジック）であることをSpringに伝える。
// Spring Securityの認証処理から自動的に呼び出される。
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // UserMapper を使ってDBからユーザーを検索する。
    // コンストラクタインジェクション：SpringがUserMapperを渡してくれる。
    private final UserMapper userMapper;

    public UserDetailsServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    // Spring Securityがログイン時に呼び出すメソッド。
    // 引数のusernameはログイン画面で入力されたユーザー名。
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // DBからusernameでユーザーを検索する。見つからなければ例外を投げてログイン失敗にする。
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("ユーザーが見つかりません: " + username);
        }
        // 見つかったユーザー情報をSpring Security用の形式に変換して返す。
        // パスワードの照合（BCryptの一致確認）はSpring Securityが自動でやってくれる。
        return org.springframework.security.core.userdetails.User
            .withUsername(user.getUsername())
            .password(user.getPassword())
            .roles("USER")  // ロール：今は全員"USER"。フェーズ2で管理者ロールなどを追加できる。
            .build();
    }

}
