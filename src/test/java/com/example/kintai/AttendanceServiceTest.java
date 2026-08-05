package com.example.kintai;

import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AttendanceServiceTest {

    @Test
void 切り捨て_0分は0分になる() {
    // 準備
    AttendanceService service = new AttendanceService(null);
    LocalTime input = LocalTime.of(9, 0);

    // 実行
    LocalTime result = service.roundTo15Minutes(input);

    // 確認
    assertEquals(LocalTime.of(9, 0), result);
}

@Test
void 切り捨て_7分は0分になる() {
    AttendanceService service = new AttendanceService(null);
    LocalTime result = service.roundTo15Minutes(LocalTime.of(9, 7));
    assertEquals(LocalTime.of(9, 0), result);
}

@Test
void 切り上げ_8分は15分になる() {
    AttendanceService service = new AttendanceService(null);
    LocalTime result = service.roundTo15Minutes(LocalTime.of(9, 8));
    assertEquals(LocalTime.of(9, 15), result);
}

@Test
void 切り上げ_14分は15分になる() {
    AttendanceService service = new AttendanceService(null);
    LocalTime result = service.roundTo15Minutes(LocalTime.of(9, 14));
    assertEquals(LocalTime.of(9, 15), result);
}

@Test
void ちょうど_15分は15分になる() {
    AttendanceService service = new AttendanceService(null);
    LocalTime result = service.roundTo15Minutes(LocalTime.of(9, 15));
    assertEquals(LocalTime.of(9, 15), result);
}

}