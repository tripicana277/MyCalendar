package test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.junit.jupiter.api.Test;

import main.item.ItemCsv;
import main.item.ItemCsv.ButtonColor;
import main.item.ItemFinal;
import main.item.LocallocaleXml;
import main.main.CalendarMain;

class CalendarMainTest extends CalendarMain {

	ItemServer initializeTest() {
		ItemServer itemServer = new CalendarMain().new ItemServer();
		readPrmFile(itemServer);
		readAllXMLFiles(itemServer);
		itemServer.setmMonth("2024年7月29日月曜日");
		return itemServer;
	}

	@Test
	void otherMonthTest() {
		// データ取得処理
		ItemServer itemServerTest = initializeTest();

		System.out.println("先月,来月カレンダー表示関数(正常)");

		// テスト1
		otherMonth(itemServerTest);
		//		System.out.print(" =" + itemServerTest.getmMonth());
		System.out.println("2024年7月30日火曜日".equals(itemServerTest.getmMonth()));

		// テスト2
		itemServerTest.setDate(LocalDate.now().plusMonths(2));
		otherMonth(itemServerTest);
		//		System.out.print(" =" + itemServerTest.getmMonth());
		System.out.println("2024年09月".equals(itemServerTest.getmMonth()));

		// テスト3
		itemServerTest.setDate(LocalDate.now().plusMonths(-2));
		otherMonth(itemServerTest);
		//		System.out.print(" =" + itemServerTest.getmMonth());
		System.out.println("2024年05月".equals(itemServerTest.getmMonth()));
	}

	@Test
	void nowTest() {
		// データ取得処理
		ItemServer itemServerTest = new CalendarMain().new ItemServer();
		readPrmFile(itemServerTest);
		readAllXMLFiles(itemServerTest);

		System.out.println("今月カレンダー表示関数(正常)");
		itemServerTest.setDate(LocalDate.parse("2024-07-29", DateTimeFormatter.ofPattern(ItemFinal.DATE_FORMAT)));

		// テスト1
		now(itemServerTest);
		//		System.out.print(" =" + itemServerTest.getmMonth());
		System.out.println("2024年7月29日月曜日".equals(itemServerTest.getmMonth()));
	}

	@Test
	void calenderSettingTest() {
		// データ取得処理
		ItemServer itemServerTest = initializeTest();

		System.out.println("カレンダー日付設定関数(正常)");

		// テスト1
		//		System.out.print(" =" + datePositionSetting(0, 1, 31, itemServerTest));
		System.out.println("".equals(datePositionSetting(0, 1, 31, itemServerTest)));

		// テスト2
		//		System.out.print(" =" + datePositionSetting(1, 7, 31, itemServerTest));
		System.out.println("1".equals(datePositionSetting(1, 7, 31, itemServerTest)));

		// テスト3
		//		System.out.print(" =" + datePositionSetting(32, 7, 31, itemServerTest));
		System.out.println("".equals(datePositionSetting(32, 7, 31, itemServerTest)));

		// テスト4
		//		System.out.print(" =" + datePositionSetting(5, 1, 31, itemServerTest));
		System.out.println("4".equals(datePositionSetting(5, 1, 31, itemServerTest)));

		// テスト5
		//		System.out.print(" =" + datePositionSetting(33, 1, 31, itemServerTest));
		System.out.println("".equals(datePositionSetting(33, 1, 31, itemServerTest)));
	}

	@Test
	void dateSettingTest() {
		// データ取得処理
		ItemServer itemServerTest = initializeTest();

		System.out.println("日付設定関数(正常)");

		// テスト1
		LocalDate testDate = LocalDate.parse("2024-07-09", DateTimeFormatter.ofPattern(ItemFinal.DATE_FORMAT));
		//				System.out.print(" =" + dateSetting(9, 7,itemServerTest));
		System.out.println(testDate.equals(dateSetting(9, 7,itemServerTest)));

		// テスト2
		LocalDate testDate2 = LocalDate.parse("2024-07-08", DateTimeFormatter.ofPattern(ItemFinal.DATE_FORMAT));
		//		System.out.print(" =" + dateSetting(9, 1, itemServerTest));
		System.out.println(testDate2.equals(dateSetting(9, 1, itemServerTest)));

		// テスト3
		LocalDate testDate3 = LocalDate.parse("2024-07-10", DateTimeFormatter.ofPattern(ItemFinal.DATE_FORMAT));
		//		System.out.print(" =" + dateSetting(10, 7, itemServerTest));
		System.out.println(testDate3.equals(dateSetting(10, 7, itemServerTest)));

		// テスト4
		LocalDate testDate4 = LocalDate.parse("2024-07-09", DateTimeFormatter.ofPattern(ItemFinal.DATE_FORMAT));
		//		System.out.print(" =" + dateSetting(10, 1, itemServerTest));
		System.out.println(testDate4.equals(dateSetting(10, 1, itemServerTest)));
	}

	@Test
	void getFilePathTest() {
		// データ取得処理
		ItemServer itemServerTest = initializeTest();

		System.out.println("ファイルパスの取得関数(正常)");

		// テスト1
		getFilePath(ItemFinal.FILE_CSV, itemServerTest);
		//		System.out.print(" =" + getFilePath(ItemFinal.FILE_CSV, itemServerTest));
		System.out.println("C:\\eclipse\\workspace\\AppCreate\\Item.csv"
				.equals(getFilePath(ItemFinal.FILE_CSV, itemServerTest)));
	}

	@Test
	void csvFileTest() {
		// データ取得処理
		ItemServer itemServerTest = initializeTest();

		System.out.println("CSVファイル(Item.csv)読込,書込関数(正常)");

		// テスト1
		LocalDate testDate = LocalDate.parse("2024-07-09", DateTimeFormatter.ofPattern(ItemFinal.DATE_FORMAT));
		StringBuilder sb = new StringBuilder();
		sb.append("6");
		sb.append(":");
		sb.append("45");
		sb.append("-");
		String string = sb.toString();
		ItemCsv itemCsv = new ItemCsv(testDate, "csvFileTest", string, ButtonColor.ORANGE);
		itemServerTest.setItemlist(testDate, itemCsv); // ItemServer にアイテムを追加
//		writeCSVFile(ItemFinal.FILE_CSV, itemServerTest); // CSV ファイルに書き込み

		ItemServer itemServerTest2 = initializeTest();
		readCSVFile(itemServerTest2);
		Map<LocalDate, ItemCsv> itemlist = itemServerTest2.getItemlist();
		ItemCsv itemCsv2 = itemlist.get(testDate);
		//		System.out.println("=" + itemCsv2.getDate());
		//		System.out.println("=" + itemCsv2.getName());
		//		System.out.println("=" + itemCsv2.getTime());
		//		System.out.println("=" + itemCsv2.getButtoncolorEnum());
		System.out.println("2024-07-09".equals(itemCsv2.getDate().toString()));
		System.out.println("csvFileTest".equals(itemCsv2.getName()));
		System.out.println("6:45-".equals(itemCsv2.getTime().toString()));
		System.out.println("ORANGE".equals(itemCsv2.getButtoncolorEnum().toString()));
	}

	@Test
	void readXMLFileTest() {
		// データ取得処理
		ItemServer itemServerTest = new CalendarMain().new ItemServer();
		readPrmFile(itemServerTest);
		itemServerTest.setmMonth("2024年7月29日月曜日");

		System.out.println("XMLファイル(Lang_xx.xml)読込関数(正常)");

		// テスト1
		readAllXMLFiles(itemServerTest); // XML ファイルを読込

		LocallocaleXml locallocaleXml = itemServerTest.getLocallocaleXml();
		//		System.out.println("=" + locallocaleXml.getFrametitle());
		//		System.out.println("=" + locallocaleXml.getLastmonth());
		//		System.out.println("=" + locallocaleXml.getThismonth());
		//		System.out.println("=" + locallocaleXml.getNextmonth());
		//		System.out.println("=" + locallocaleXml.getSave());
		//		System.out.println("=" + locallocaleXml.getLoad());
		//		System.out.println("=" + locallocaleXml.getSunday());
		//		System.out.println("=" + locallocaleXml.getMonday());
		//		System.out.println("=" + locallocaleXml.getTuesday());
		//		System.out.println("=" + locallocaleXml.getWednesday());
		//		System.out.println("=" + locallocaleXml.getThursday());
		//		System.out.println("=" + locallocaleXml.getFriday());
		//		System.out.println("=" + locallocaleXml.getSaturday());
		//		System.out.println("=" + locallocaleXml.getDialogtitle());
		//		System.out.println("=" + locallocaleXml.getDate());
		//		System.out.println("=" + locallocaleXml.getName());
		//		System.out.println("=" + locallocaleXml.getTime());
		//		System.out.println("=" + locallocaleXml.getFinishdate());
		//		System.out.println("=" + locallocaleXml.getButtoncolorEnum());
		//		System.out.println("=" + locallocaleXml.getHour());
		//		System.out.println("=" + locallocaleXml.getMin());
		//		System.out.println("=" + locallocaleXml.getDialogdeletetitle());
		//		System.out.println("=" + locallocaleXml.getDelete());
		//		System.out.println("=" + locallocaleXml.getAlarm());
		//		System.out.println("=" + locallocaleXml.getDateformat());
		//		System.out.println("=" + locallocaleXml.getDialogexceptiontitle());
		//		System.out.println("=" + locallocaleXml.getException1());
		//		System.out.println("=" + locallocaleXml.getException2());
		//		System.out.println("=" + locallocaleXml.getException3());
		//		System.out.println("=" + locallocaleXml.getException4());
		System.out.println("カレンダー".equals(locallocaleXml.getFrametitle()));
		System.out.println("先月".equals(locallocaleXml.getLastmonth()));
		System.out.println("今月".equals(locallocaleXml.getThismonth()));
		System.out.println("来月".equals(locallocaleXml.getNextmonth()));
		System.out.println("保存".equals(locallocaleXml.getSave()));
		System.out.println("読込".equals(locallocaleXml.getLoad()));
		System.out.println("日".equals(locallocaleXml.getSunday()));
		System.out.println("月".equals(locallocaleXml.getMonday()));
		System.out.println("火".equals(locallocaleXml.getTuesday()));
		System.out.println("水".equals(locallocaleXml.getWednesday()));
		System.out.println("木".equals(locallocaleXml.getThursday()));
		System.out.println("金".equals(locallocaleXml.getFriday()));
		System.out.println("土".equals(locallocaleXml.getSaturday()));
		System.out.println("予定の設定".equals(locallocaleXml.getDialogtitle()));
		System.out.println("作業日".equals(locallocaleXml.getDate()));
		System.out.println("作業予定名称".equals(locallocaleXml.getName()));
		System.out.println("予定開始時間(xx:xx)".equals(locallocaleXml.getTime()));
		System.out.println("作業終了日".equals(locallocaleXml.getFinishdate()));
		System.out.println("予定の表示色".equals(locallocaleXml.getButtoncolorEnum()));
		System.out.println("時".equals(locallocaleXml.getHour()));
		System.out.println("分".equals(locallocaleXml.getMin()));
		System.out.println("予定の削除".equals(locallocaleXml.getDialogdeletetitle()));
		System.out.println("予定を削除しますか？".equals(locallocaleXml.getDelete()));
		System.out.println("作業予定名称を入力して下さい".equals(locallocaleXml.getAlarm()));
		System.out.println("yyyy年MM月".equals(locallocaleXml.getDateformat()));
		System.out.println("例外".equals(locallocaleXml.getDialogexceptiontitle()));
		System.out.println("処理で例外が発生しました。".equals(locallocaleXml.getException1()));
		System.out.println("エラー名称:".equals(locallocaleXml.getException2()));
		System.out.println("アプリの全データをバックアップし".equals(locallocaleXml.getException3()));
		System.out.println("再起動して下さい。".equals(locallocaleXml.getException4()));
	}

	@Test
	void readPrmFileTest() {
		// データ取得処理
		ItemServer itemServerTest = new CalendarMain().new ItemServer();
		readAllXMLFiles(itemServerTest);
		itemServerTest.setmMonth("2024年7月29日月曜日");

		System.out.println("Prmファイル(Prm.ini)読込関数(正常)");

		// テスト1
		readPrmFile(itemServerTest); // Prm ファイルの読込
		//		System.out.println("=" + itemServerTest.getLocal().toString());
		//		System.out.println("=" + itemServerTest.getWindowSizeX());
		//		System.out.println("=" + itemServerTest.getWindowSizeY());
		//		System.out.println("=" + itemServerTest.getFontSizeS());
		//		System.out.println("=" + itemServerTest.getFontSizeL());
		System.out.println("ja_JP".equals(itemServerTest.getLocal().toString()));
		System.out.println(65 == itemServerTest.getWindowSizeX());
		System.out.println(105 == itemServerTest.getWindowSizeY());
		System.out.println(12 == itemServerTest.getFontSizeS());
		System.out.println(17 == itemServerTest.getFontSizeL());
	}
}
