package com.example.kintai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Spring Boot アプリケーションのエントリーポイント（起動クラス）
// @SpringBootApplication：自動設定・コンポーネントスキャンを有効にするアノテーション
// Spring Boot プロジェクト作成時に自動生成される。基本的に編集不要。
@SpringBootApplication
public class KintaiApplication {

	// アプリ起動時に最初に呼ばれるメソッド
	// SpringApplication.run() で Spring Boot が起動し、組み込みTomcatサーバーが立ち上がる
	public static void main(String[] args) {
		SpringApplication.run(KintaiApplication.class, args);
	}

}
