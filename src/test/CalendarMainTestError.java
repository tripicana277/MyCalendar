package test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import main.item.ItemFinal;
import main.main.CalendarMain;

class CalendarMainTestError extends CalendarMain {

	ItemServer initializeTest() {
		ItemServer itemServer = new CalendarMain().new ItemServer();
		readPrmFile(itemServer);
		readAllXMLFiles(itemServer);
		itemServer.setmMonth("2024年7月29日月曜日");
		return itemServer;
	}

	/**
	* NullLog出力 及び ダイアログ表示試験
	*/
	@Test
	void otherMonthTest() {
		// データ取得処理
		ItemServer itemServerTest = initializeTest();

		System.out.println("先月,来月カレンダー表示関数(異常)");

		// テスト1
		itemServerTest.setDate(null);
		//		otherMonth(itemServerTest);
	}

	@Test
	void nowTest() {
		// データ取得処理
		ItemServer itemServerTest = new CalendarMain().new ItemServer();
		readPrmFile(itemServerTest);
		readAllXMLFiles(itemServerTest);

		System.out.println("今月カレンダー表示関数(異常)");

		// テスト1
		//		itemServerTest.setDate(null);
		//		now(itemServerTest);
	}

	@Test
	void dateSettingTest() {
		// データ取得処理
		ItemServer itemServerTest = initializeTest();

		System.out.println("日付設定関数(異常)");

		// テスト1
		LocalDate testDate = LocalDate.parse("2024-07-09", DateTimeFormatter.ofPattern(ItemFinal.DATE_FORMAT));
				System.out.println(testDate.equals(dateSetting(-9, -7, itemServerTest)));
	}

	@Test
	void getFilePathTest() {
		// データ取得処理
		ItemServer itemServerTest = initializeTest();

		System.out.println("ファイルパスの取得関数(異常)");

		// テスト1
		getFilePath(ItemFinal.FILE_CSV, itemServerTest);
		//		System.out.println("C:\\eclipse\\workspace\\AppCreate\\Item.csv"
		//				.equals(getFilePath(null, itemServerTest)));
	}

	@Test
	void readPrmFileTest() {
		// データ取得処理
		ItemServer itemServerTest = new CalendarMain().new ItemServer();
		readAllXMLFiles(itemServerTest);

		// テスト1
		// Prm.iniを消して試験
		//		System.out.println("Prmファイル(Prm.ini)読込関数(異常)");
		//		readPrmFile(itemServerTest);
	}
}
