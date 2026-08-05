package com.example.kintai;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

// @Mapper をつけると、Spring が起動時にこのインターフェースの実装クラスを自動生成してくれる
// SQL は HolidayMapper.xml に書く。このインターフェースとXMLがメソッド名で紐づく
@Mapper
public interface HolidayMapper {
    // 指定期間の祝日レコードを全件取得する
    List<Holiday> findByDateBetween(LocalDate start, LocalDate end);
}
