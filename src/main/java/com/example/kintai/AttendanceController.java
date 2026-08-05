package com.example.kintai;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

// @Controller をつけると、Spring がこのクラスをコントローラーとして管理する
// ブラウザからのリクエストを受け取り、HTMLを返したりリダイレクトしたりする役割
@Controller
public class AttendanceController {

    private final UserRepository userRepository;
    private final AttendanceService attendanceService;

    // コンストラクタインジェクション：必要なクラスを Spring が自動でセットしてくれる
    public AttendanceController(UserRepository userRepository,
                            AttendanceService attendanceService) {
        this.userRepository = userRepository;
        this.attendanceService = attendanceService;
    }

    // 打刻画面の表示（GET）
    // Principal = ログイン中のユーザー情報。principal.getName() でユーザー名が取れる
    // Model = コントローラーからHTMLにデータを渡すための入れ物
    @GetMapping("/attendance/stamp")
    public String stamp(Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                                  .orElseThrow(); // ユーザーが見つからなければ例外
        Attendance today = attendanceService.getTodayAttendance(user); // 今日の打刻を取得
        model.addAttribute("attendance", today); // "attendance" という名前でHTMLに渡す
        return "stamp"; // templates/stamp.html を表示
    }

    // 出勤ボタン押下（POST）
    @PostMapping("/attendance/clock-in")
    public String clockIn(Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                                  .orElseThrow();
        attendanceService.clockIn(user); // 出勤打刻を保存
        return "redirect:/attendance/stamp"; // 保存後に打刻画面へリダイレクト
    }

    // 退勤ボタン押下（POST）
    @PostMapping("/attendance/clock-out")
    public String clockOut(Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                                  .orElseThrow();
        attendanceService.clockOut(user); // 退勤打刻を保存
        return "redirect:/attendance/stamp"; // 保存後に打刻画面へリダイレクト
    }

}
