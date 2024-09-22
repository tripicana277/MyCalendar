package main.item;

import java.awt.Color;

/**
 * 定数定義用クラス
 */
public class ItemFinal {
	public static final String APPVER = " AppVer:1.0.0";// Appバージョン
	public static final String SMALL = "s"; // サイズ: 小
	public static final String MIDDLE = "m"; // サイズ: 中
	public static final String LARGE = "l"; // サイズ: 大
	public static final String BLANK = ""; // 空文字列
	public static final String COMMA = ","; // カンマ
	public static final String NEWLINE = "\n"; // 改行
	public static final String CUMMA_NEWLINE = ",|\n"; // カンマと改行
	public static final String COLON = ":"; // コロン	
	public static final String HYPHEN = "-"; // ハイフン	
	public static final String MONTH_FORMAT = "yyyy-MM-"; // 月のフォーマット
	public static final String DATE_FORMAT = "yyyy-MM-dd"; // 日付のフォーマット
	public static final String FONT = "Meiryo"; // フォント名
	public static final String LOCALE_JP = "ja"; // 日本語ロケール
	public static final String LOCALE_US = "en"; // 英語ロケール
	public static final String FILE_CSV = "Item.csv"; // CSVファイル名
	public static final String FILE_XML_JP = "Lang_jp.xml";// 日本語XMLファイル名
	public static final String FILE_XML_EN = "Lang_en.xml";// 英語XMLファイル名
	public static final String FILE_INI = "Prm.ini"; // 設定INIファイル名
	public static final String FILE_LOG = "error.log"; // Logファイル名
	public static final String FILE_ICON = "/icon.ico"; // 設定アイコンファイル名
	public static final String BRACKETS = "<"; // ブラケット記号
	public static final String OK = "OK"; // OKボタンのテキスト
	public static final int SAT_COUNT = 6; // 土曜日の値
	public static final int WEEK_MAX = 7; // 週の日数
	public static final int HOUR_PRV = -1; // 先月
	public static final int HOUR_NEXT = 1; // 来月
	public static final int TWO_DIG = 10; // 2桁
	public static final String ZERO_PAD = "0"; // 0埋め
	public static final int WHEEL_BUTTON = 2; // マウスホイールボタン

	public static final String[] HOUR = { // 時間の選択肢
			"06", "07", "08", "09", "10", "11", "12",
			"13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"
	};
	public static final String[] MIN = { // 分の選択肢
			"00", "15", "30", "45"
	};

	// カスタムカラー定義
	public static final Color CUSTOM_RED = new Color(255, 151, 203); // 赤
	public static final Color CUSTOM_BLUE = new Color(147, 147, 255); // 青
	public static final Color CUSTOM_GREEN = new Color(0, 234, 189); // 緑
	public static final Color CUSTOM_CYAN = new Color(177, 206, 249); // シアン
	public static final Color CUSTOM_ORANGE = new Color(248, 198, 40); // オレンジ
	public static final Color CUSTOM_DARK_ORANGE = new Color(231, 178, 7); // ダークオレンジ
	public static final Color CUSTOM_DARK_RED = new Color(204, 51, 0); // ダークレッド
	public static final Color CUSTOM_DARK_BLUE = new Color(0, 102, 255); // ダークブルー
	public static final Color CUSTOM_DARK_GRAY = new Color(95, 95, 95); // ダークグレー
	public static final Color CUSTOM_LIGHT_GRAY = new Color(217, 217, 217); // ライトグレー
	public static final Color CUSTOM_DARK_BROWN = new Color(73, 34, 7); // ダークブラウン
}
