package com.example.kintai;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class AttendanceServiceIntegrationTest {

    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private UserRepository userRepository;

    @Test
    void 出勤打刻するとDBに保存される() {
    User user = userRepository.findByUsername("ayumu").get();

    Attendance result = attendanceService.clockIn(user);

    assertEquals(user, result.getUser());
    assertNotNull(result.getClockIn());
    assertNull(result.getClockOut());
}
}
