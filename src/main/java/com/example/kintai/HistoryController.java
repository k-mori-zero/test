package com.example.kintai;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// @Controller をつけると、Spring がこのクラスを「画面を返す担当」として管理する
@Controller
public class HistoryController {

    private final UserRepository userRepository;
    private final AttendanceService attendanceService;
    private final HolidayMapper holidayMapper;

    // コンストラクタインジェクション：必要なクラスを Spring が自動でセットしてくれる
    public HistoryController(UserRepository userRepository,
                        AttendanceService attendanceService,
                        HolidayMapper holidayMapper) {
        this.userRepository = userRepository;
        this.attendanceService = attendanceService;
        this.holidayMapper = holidayMapper;
    }

    // GET /attendance/history：履歴一覧画面を表示する
    // @RequestParam(required = false)：URLの ?year=2026&month=6 を受け取る。なければ null
    @GetMapping("/attendance/history")
    public String history(Model model, Principal principal,
        @RequestParam(required = false) Integer year,
        @RequestParam(required = false) Integer month) {
        // URLパラメータがない場合（初回アクセス時）は当月をデフォルトにする
        if (year == null) year = LocalDate.now().getYear();
        if (month == null) month = LocalDate.now().getMonthValue();
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        // 月初（例: 2026-06-01）と月末（例: 2026-06-30）を計算する
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        // 当月の打刻データを取得する
        List<Attendance> attendances = attendanceService.getMonthAttendances(user, year, month);
        // 月初〜月末の全日付リストを生成する（例: [2026-06-01, 2026-06-02, ... 2026-06-30]）
        List<LocalDate> allDays = start.datesUntil(end.plusDays(1)).toList();
        // ドロップダウンの選択状態を保持するために年月をModelに渡す
        model.addAttribute("selectedYear", year);
        model.addAttribute("selectedMonth", month);
        model.addAttribute("allDays", allDays);
        // attendancesリスト（順番付きリスト）をMap（日付→打刻データ）に変換する
        // HTML側で attendanceMap.get(day) と書くだけでその日の打刻データを取り出せる
        Map<LocalDate, Attendance> attendanceMap = new java.util.HashMap<>();
        for (Attendance a : attendances) {
            attendanceMap.put(a.getDate(), a);
        }
        model.addAttribute("attendanceMap", attendanceMap);
        // 当月の祝日データを取得して、日付だけのSetに変換する
        // HTML側で holidayDates.contains(day) と書くだけで祝日かどうか判定できる
        List<Holiday> holidays = holidayMapper.findByDateBetween(start, end);
        Set<LocalDate> holidayDates = new java.util.HashSet<>();
        for (Holiday h : holidays) {
            holidayDates.add(h.getDate());
        }
        model.addAttribute("holidayDates", holidayDates);
        return "history";
    }

    // POST /attendance/comment：コメントを保存してリダイレクトする
    // @RequestParam String date：hidden input で送られてきた日付文字列（例: "2026-06-17"）
    @PostMapping("/attendance/comment")
    public String saveComment(Principal principal,
        @RequestParam String date,
        @RequestParam String comment) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        // 文字列の日付を LocalDate に変換してサービスに渡す
        attendanceService.saveComment(user, LocalDate.parse(date), comment);
        // 保存後は履歴画面にリダイレクトする（redirect: をつけると GET リクエストとして再アクセスされる）
        return "redirect:/attendance/history";
    }

}
