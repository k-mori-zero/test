package com.example.kintai;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// @Configuration：このクラスがSpringの設定クラスであることを示す。
// Spring Securityの認証・認可ルールをここにまとめて書く。
@Configuration
public class SecurityConfig {

    // @Bean：このメソッドの戻り値をSpringが管理するオブジェクトとして登録する。
    // SecurityFilterChain：どのURLにどんなセキュリティルールを適用するかを定義する。
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // URLごとのアクセス制御を設定する。
            .authorizeHttpRequests(auth -> auth
                // /h2-console 以下は認証なしで誰でもアクセスOK（開発用DBコンソール）。
                .requestMatchers("/h2-console/**", "/login").permitAll()
                // それ以外のURLはすべてログインが必要。
                .anyRequest().authenticated()
            )
            // フォームログインの設定。
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/", true)
            )
            // CSRF（クロスサイトリクエストフォージェリ）対策の設定。
            // H2コンソールはiframeを使うためCSRFチェックを除外しないと動かない。
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
            )
            // H2コンソールはiframeで表示するため、同じサイトからのiframeを許可する。
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            );
        return http.build();
    }

    // BCryptPasswordEncoder をSpring全体で使えるように登録する。
    // UserDetailsServiceImpl がパスワード照合に使う。
    // これがないと「どのアルゴリズムでハッシュを照合すればいいか」が分からずログイン失敗になる。
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
