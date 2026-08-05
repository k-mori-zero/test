package com.example.kintai;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// @Controller：このクラスがコントローラー（URLとHTMLを紐づける役割）であることをSpringに伝える。
@Controller
public class HomeController {

    // @GetMapping("/")：ブラウザから localhost:8080/ にGETリクエストが来たときにこのメソッドを呼ぶ。
    // ログイン成功後、SecurityConfig の設定により / へ転送されてここが呼ばれる。
    @GetMapping("/")
    public String home() {
        // "home" を返すと、Spring Boot が templates/home.html を探してブラウザに送る。
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
