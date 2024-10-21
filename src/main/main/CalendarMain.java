package main.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import main.component.JCustomButton;
import main.component.JCustomDialog;
import main.component.JCustomFrame;
import main.component.JCustomlPanel;
import main.component.JCustomlabel;
import main.item.ItemCsv;
import main.item.ItemCsv.ButtonColor;
import main.item.ItemFinal;
import main.item.LocallocaleXml;

/**
 * カレンダークラス
 * このカレンダーは予定の確認とスケジュール管理を
 * サポートしています。
 * @author S.Takeda
 * @version 1.0
 */
public class CalendarMain {

	/**
	 * デフォルトコンストラクタ。
	 * CalendarMain クラスの新しいインスタンスを作成します。
	 */
	public CalendarMain() {
	}

	/**
	* Main関数
	* AppVer 1.0.0
	* @param args mainメソッド引数
	*/
	public static void main(String[] args) {

		// 定義データ保持用インナークラスのインスタンスを作成
		ItemServer itemServer = new CalendarMain().new ItemServer();

		// プロパティファイル(Prm.ini)の読込
		readPrmFile(itemServer);

		// デフォルトフォント設定(メイリオ)
		setUIFont(new javax.swing.plaf.FontUIResource(ItemFinal.FONT, Font.PLAIN, itemServer.fontSizeS),
				itemServer);

		// 全XMLファイルの読込
		readAllXMLFiles(itemServer);

		// フレーム作成処理
		itemServer.frame = new JCustomFrame(itemServer.getLocallocaleXml().getFrametitle(), itemServer);

		// アイコンをロード（プロジェクト内に配置された画像）
		ImageIcon icon = new ImageIcon(CalendarMain.class.getResource(ItemFinal.FILE_ICON));
		itemServer.frame.setIconImage(icon.getImage());

		/**
		* 先月のボタン
		*/
		JCustomButton buttonPrev = new JCustomButton(itemServer.getLocallocaleXml().getLastmonth());
		buttonPrev.addActionListener(e -> {
			itemServer.date = itemServer.date.plusMonths(ItemFinal.HOUR_PRV);
			otherMonth(itemServer);
			calenderSetting(true, itemServer);
		});

		/**
		* 今月のボタン
		*/
		JCustomButton buttonNow = new JCustomButton(itemServer.getLocallocaleXml().getThismonth());
		buttonNow.addActionListener(e -> {
			itemServer.date = LocalDate.now();
			now(itemServer);
			calenderSetting(true, itemServer);
		});

		/**
		* 来月のボタン
		*/
		JCustomButton buttonNext = new JCustomButton(itemServer.getLocallocaleXml().getNextmonth());
		buttonNext.addActionListener(e -> {
			itemServer.date = itemServer.date.plusMonths(ItemFinal.HOUR_NEXT);
			otherMonth(itemServer);
			calenderSetting(true, itemServer);
		});

		
		/**
		* 来月のボタン
		*/
		JCustomButton buttonHelp = new JCustomButton(itemServer.getLocallocaleXml().getNextmonth());
		buttonNext.addActionListener(e -> {
			itemServer.date = itemServer.date.plusMonths(ItemFinal.HOUR_NEXT);
			otherMonth(itemServer);
			calenderSetting(true, itemServer);
		});

		
		// 空白ラベルの設定(レイアウト上意図的に設定)
		JLabel labelNext = new JLabel(ItemFinal.BLANK);
		labelNext.setBackground(Color.WHITE);
		labelNext.setOpaque(true);

		// ボタン用パネルをフレームに追加
		JCustomlPanel panelButton = new JCustomlPanel(buttonPrev, buttonNow, buttonNext, labelNext);
		itemServer.frame.add(panelButton);

		/**
		* 月の表示パネル
		*/
		JPanel panelMonth = new JPanel(new BorderLayout());
		now(itemServer);
		itemServer.label = new JCustomlabel(itemServer.getmMonth().toString(), itemServer);
		panelMonth.add(itemServer.label);
		itemServer.frame.add(panelMonth);

		/**
		* 週の表示パネル
		*/
		// GridLayoutを使用して1行7列のJPanelを作成
		JPanel panelWeek = new JPanel(new GridLayout(1, 7));

		// 各曜日の名前をリストに追加
		List<String> listWeek = List.of(
				itemServer.getLocallocaleXml().getSunday(),
				itemServer.getLocallocaleXml().getMonday(),
				itemServer.getLocallocaleXml().getTuesday(),
				itemServer.getLocallocaleXml().getWednesday(),
				itemServer.getLocallocaleXml().getThursday(),
				itemServer.getLocallocaleXml().getFriday(),
				itemServer.getLocallocaleXml().getSaturday());

		// 各曜日のラベルを作成し、JPanelに追加
		listWeek.forEach(e -> {
			JCustomlabel labelWeek = new JCustomlabel(e, JLabel.CENTER, itemServer);
			panelWeek.add(labelWeek);
		});

		// JFrameにpanWeekを追加
		itemServer.frame.add(panelWeek);

		/**
		* 日の表示パネル
		*/
		itemServer.panel = new JPanel(new GridLayout(6, 7));
		readCSVFile(itemServer);
		calenderSetting(false, itemServer);

		// フレームのパックと可視化
		itemServer.frame.pack();
		itemServer.frame.setVisible(true);

		System.out.println("アプリケーションが起動しました。");
	}

	/**
	* 先月,来月カレンダー表示関数
	* @param itemServer ItemServerオブジェクト
	*/
	public static void otherMonth(ItemServer itemServer) {
		try {
			if (itemServer.date.format(DateTimeFormatter.ofPattern(ItemFinal.MONTH_FORMAT))
					.equals(LocalDate.now().format(DateTimeFormatter.ofPattern(ItemFinal.MONTH_FORMAT)))) {
				// 現在の月と同じ場合、ロケールに基づいた完全な日付形式で表示
				itemServer.setmMonth(LocalDate.now()
						.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
								.withLocale(itemServer.locale)));
			} else {
				// 現在の日付と異なる場合、XMLで指定された日付形式で表示
				DateTimeFormatter dateTimeFormat = DateTimeFormatter
						.ofPattern(itemServer.getLocallocaleXml().getDateformat(), itemServer.locale);
				itemServer.setmMonth(dateTimeFormat.format(itemServer.date));
			}
		} catch (Exception e) {
			// スタックトレースを(error.log)に出力しダイアログ表示
			try {
				e.printStackTrace(new PrintStream(new FileOutputStream(ItemFinal.FILE_LOG)));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			exceptionDialogComponentSetting(itemServer, e);
		}
	}

	/**
	 * 今月カレンダー表示関数
	 * @param itemServer ItemServerオブジェクト
	 */
	public static void now(ItemServer itemServer) {

		try {
			// ロケールに基づいた完全な日付形式で表示
			itemServer.setmMonth(itemServer.date
					.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
							.withLocale(itemServer.locale)));
		} catch (Exception e) {
			// スタックトレースを(error.log)に出力しダイアログ表示
			try {
				e.printStackTrace(new PrintStream(new FileOutputStream(ItemFinal.FILE_LOG)));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			exceptionDialogComponentSetting(itemServer, e);
		}
	}

	/**
	* カレンダー日付設定関数
	 * @param flagBoolean 初期起動フラグ
	 * @param itemServer ItemServerオブジェクト
	*/
	public static void calenderSetting(boolean flagBoolean, ItemServer itemServer) {

		try {
			// 初日の設定：現在の月の初日を取得
			final LocalDate firstDate = itemServer.date.withDayOfMonth(1);

			// 初日の曜日番号を取得（1 = 月曜日, 7 = 日曜日）
			final int preSpace = firstDate.getDayOfWeek().getValue();

			// 現在の月の長さ（最大日数）を取得
			final int length = itemServer.date.lengthOfMonth();

			String position = null;
			for (int count = 1; count < 43; count++) {

				// 日付位置設定関数
				position = datePositionSetting(count, preSpace, length, itemServer);

				// コンポーネント設定関数
				componentSetting(count, flagBoolean, position, itemServer);

				// 現在のカウントに基づいて、日付を設定
				if ((preSpace < ItemFinal.WEEK_MAX && preSpace < count && count <= length + preSpace)
						|| (preSpace == ItemFinal.WEEK_MAX && count <= length)) {

					// 日付設定関数
					LocalDate date = dateSetting(count, preSpace, itemServer);

					// スケジュール設定関数
					scheduleSetting(date, position, itemServer);
				}

				// 今日の日付をマークする
				todayMark(count, preSpace, itemServer);
				itemServer.panel.add(itemServer.objButton);
			}
			// 設定したパネルをフレームに追加
			itemServer.frame.add(itemServer.panel);

		} catch (Exception e) {
			// スタックトレースを(error.log)に出力しダイアログ表示
			try {
				e.printStackTrace(new PrintStream(new FileOutputStream(ItemFinal.FILE_LOG)));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			exceptionDialogComponentSetting(itemServer, e);
		}
	}

	/**
	 * 日付位置設定関数
	 * @param count インデックス値
	 * @param preSpace 初日の曜日番号
	 * @param length 月の長さを設定
	 * @param itemServer ItemServerオブジェクト
	 * @return 設定した日付または空白
	 */
	protected static String datePositionSetting(int count, int preSpace, int length, ItemServer itemServer) {
		try {
			String date = null;
			if (preSpace != ItemFinal.WEEK_MAX && count <= preSpace) { // 月初前の空白を作成する条件
				date = ItemFinal.BLANK;
			} else if (count <= (length + preSpace)) { // カレンダーの生成条件

				if (preSpace == ItemFinal.WEEK_MAX && count <= length) {
					// 月初が日曜日の場合
					// 日付を文字列に変換
					date = String.valueOf(count);
				} else if (preSpace == ItemFinal.WEEK_MAX && count > length) {
					// 月末後の空白
					date = ItemFinal.BLANK;
				} else {
					// 月初が日曜日以外の場合

					// 日付を文字列に変換
					date = String.valueOf(count - preSpace);
				}
			} else {
				// 月末後の空白を作成する条件
				date = ItemFinal.BLANK;
			}
			return date; // 設定した日付または空白を返す

		} catch (Exception e) {
			// スタックトレースを(error.log)に出力しダイアログ表示
			try {
				e.printStackTrace(new PrintStream(new FileOutputStream(ItemFinal.FILE_LOG)));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			exceptionDialogComponentSetting(itemServer, e);
			return null;
		}
	}

	/**
	 * コンポーネント設定関数
	 * @param count インデックス値
	 * @param flagBoolean フラグ
	 * @param text 設定する文字列
	 * @param itemServer ItemServerオブジェクト
	 */
	protected static void componentSetting(int count, boolean flagBoolean, String text, ItemServer itemServer) {
		try {
			// 初回フラグが false の場合、ボタンを新規作成
			if (flagBoolean == false) {
				// 新しいカスタムボタンを作成し、指定されたサイズとテキストを設定
				itemServer.objButton = new JCustomButton(text,
						new Dimension(itemServer.windowSizeX, itemServer.windowSizeY), itemServer);

				// 曜日によって文字色を変更
				int week = (count + ItemFinal.SAT_COUNT) % ItemFinal.WEEK_MAX; // count を使って曜日を計算
				if (week == 6) { // 土曜日の場合
					itemServer.objButton.setForeground(ItemFinal.CUSTOM_DARK_BLUE); // 土曜日の文字色を設定
				} else if (week == 0) { // 日曜日の場合
					itemServer.objButton.setForeground(ItemFinal.CUSTOM_DARK_RED); // 日曜日の文字色を設定
				} else {
					itemServer.objButton.setForeground(ItemFinal.CUSTOM_DARK_BROWN); // 土,日以外の文字色を設定
				}
				// ボタンをボタンリストに追加
				itemServer.lstButtons.add(itemServer.objButton);

			} else {
				// 月のラベルを設定（再表示時）
				itemServer.label.setText(itemServer.getmMonth().toString());

				// ボタンリストから既存のボタンを取得し、テキストを更新
				itemServer.objButton = itemServer.lstButtons.get(count - 1);
				itemServer.objButton.setText(text);
			}

			// ボタンの背景色を白に設定
			itemServer.objButton.setBackground(Color.WHITE);

			// 日付が設定されていないボタンは「灰色」に設定
			if (text.equals(ItemFinal.BLANK)) {
				itemServer.objButton.setBackground(ItemFinal.CUSTOM_DARK_GRAY);
			}
			// ボタンの枠線を茶色に設定
			itemServer.objButton.setBorder(new LineBorder(ItemFinal.CUSTOM_DARK_BROWN, 1));

		} catch (Exception e) {
			// スタックトレースを(error.log)に出力しダイアログ表示
			try {
				e.printStackTrace(new PrintStream(new FileOutputStream(ItemFinal.FILE_LOG)));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			exceptionDialogComponentSetting(itemServer, e);
		}
	}

	/**
	* 日付設定関数
	* @param count インデックス値
	* @param preSpace 初日の曜日番号
	* @param itemServer ItemServerオブジェクト
	* @return 日付設定
	*/
	protected static LocalDate dateSetting(int count, int preSpace, ItemServer itemServer) {

		try {
			// 予定表示処理のためにカレンダーの日付を設定する
			String date = null;
			Integer integer = null;
			StringBuilder sb = new StringBuilder();

			// StringBuilder を初期化（文字列の比較処理を開始する前に内容をクリア）
			sb.delete(0, sb.length());

			// 現在の月を `ItemFinal.MONTH_FORMAT` の形式で取得して文字列に変換
			date = itemServer.date.format(DateTimeFormatter.ofPattern(ItemFinal.MONTH_FORMAT));
			sb.append(date);

			// 日付を計算する
			if (preSpace == ItemFinal.WEEK_MAX) {
				// 月初が日曜日の場合、count を使用
				integer = Integer.valueOf(count);
			} else {
				// 月初が日曜日以外の場合、曜日のオフセットを考慮
				integer = Integer.valueOf(count - preSpace);
			}

			if (integer.intValue() < ItemFinal.TWO_DIG) {
				// 1桁の日付の場合、先頭にゼロを追加
				sb.append(ItemFinal.ZERO_PAD);
			}

			// 計算した日付を文字列に追加
			sb.append(integer.toString());
			date = sb.toString();

			// 完成した日付文字列を ItemFinal.DATE_FORMAT の形式で LocalDate に変換
			return LocalDate.parse(date, DateTimeFormatter.ofPattern(ItemFinal.DATE_FORMAT));

		} catch (Exception e) {
			// スタックトレースを(error.log)に出力しダイアログ表示
			try {
				e.printStackTrace(new PrintStream(new FileOutputStream(ItemFinal.FILE_LOG)));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			exceptionDialogComponentSetting(itemServer, e);
		}
		return null;
	}

	/**
	 * スケジュール設定関数
	 * @param date 日付オブジェクト
	 * @param strDate 日付または空白
	 * @param itemServer ItemServerオブジェクト
	 */
	protected static void scheduleSetting(LocalDate date, String strDate, ItemServer itemServer) {
		try {
			// 指定した日付の予定が itemlist に存在し、itemlist が空でない場合
			if (itemServer.itemlist.containsKey(date)
					&& !itemServer.itemlist.isEmpty()) {

				// 該当の日付の予定を取得
				ItemCsv objitem = itemServer.itemlist.get(date);

				// HTML 形式で表示内容を構築
				String tmpString = "<HTML>"
						+ strDate // 日付
						+ "<BR />" // 改行
						+ objitem.getTime() // 時間
						+ "<BR />" // 改行
						+ objitem.getName() // 名前
						+ "</HTML>";

				// ボタンに表示内容を設定
				itemServer.objButton.setText(tmpString);

				// ボタンの背景色を設定
				switch (objitem.getButtoncolorEnum()) {
				case RED:
					itemServer.objButton.setBackground(ItemFinal.CUSTOM_RED); // 赤色
					break;
				case BLUE:
					itemServer.objButton.setBackground(ItemFinal.CUSTOM_BLUE); // 青色
					break;
				case GREEN:
					itemServer.objButton.setBackground(ItemFinal.CUSTOM_GREEN); // 緑色
					break;
				case CYAN:
					itemServer.objButton.setBackground(ItemFinal.CUSTOM_CYAN); // シアン
					break;
				case ORANGE:
					itemServer.objButton.setBackground(ItemFinal.CUSTOM_ORANGE); // オレンジ
					break;
				default:
					itemServer.objButton.setBackground(Color.WHITE); // デフォルト（白色）

					// 色設定のエラーをコンソールに表示
					throw new Exception("Button Color Error");
				}
			}
		} catch (Exception e) {
			// スタックトレースを(error.log)に出力しダイアログ表示
			try {
				e.printStackTrace(new PrintStream(new FileOutputStream(ItemFinal.FILE_LOG)));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			exceptionDialogComponentSetting(itemServer, e);
		}
	}

	/**
	 * 今日の日付をマークする関数
	 * @param count インデックス値
	 * @param preSpace 初日の曜日番号
	 * @param itemServer ItemServerオブジェクト
	 */
	protected static void todayMark(int count, int preSpace, ItemServer itemServer) {

		try {
			// itemServer.dateが現在の日付と一致するか確認
			if (itemServer.date.format(DateTimeFormatter.ofPattern(ItemFinal.MONTH_FORMAT))
					.equals(LocalDate.now().format(DateTimeFormatter.ofPattern(ItemFinal.MONTH_FORMAT)))) {
				// 現在の月の場合のみ
				// countが現在の日付の日にちに対応するか確認
				int today = LocalDate.now().getDayOfMonth();
				int adjustedDay;
				if (preSpace == ItemFinal.WEEK_MAX) {
					// 月初が日曜の場合今日の日付と比較
					adjustedDay = today;
				} else {
					// 月初が日曜の場合今日の日付に初日の曜日番号を加算して比較
					adjustedDay = today + preSpace;
				}
				if (count == adjustedDay) {
					// 本日のボタンに枠線を設定（ダークレッド、太さ2ピクセル）
					itemServer.objButton.setBorder(new LineBorder(ItemFinal.CUSTOM_DARK_RED, 2, true));
				}
			}
		} catch (Exception e) {
			// スタックトレースを(error.log)に出力しダイアログ表示
			try {
				e.printStackTrace(new PrintStream(new FileOutputStream(ItemFinal.FILE_LOG)));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			exceptionDialogComponentSetting(itemServer, e);
		}
	}

	/**
	* ファイルパスの取得関数
	* @param fileName ファイル名称
	* @param itemServer ItemServerオブジェクト
	* @return 日付設定 ファイルパス
	*/
	protected static String getFilePath(String fileName, ItemServer itemServer) {
		try {
			// 指定されたファイル名で File オブジェクトを作成
			final File file = new File(fileName);

			// ファイルが存在しない場合、新しいファイルを作成
			if (file.exists() == false) {
				file.createNewFile();
			}
			// ファイルの絶対パスを返す
			return file.getAbsolutePath();

		} catch (Exception e) {
			// スタックトレースを(error.log)に出力しダイアログ表示
			try {
				e.printStackTrace(new PrintStream(new FileOutputStream(ItemFinal.FILE_LOG)));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			exceptionDialogComponentSetting(itemServer, e);
		}
		return null;
	}

	/**
	* CSVファイル(Item.csv)の読込関数
	* @param itemServer ItemServerオブジェクト
	*/
	protected static void readCSVFile(ItemServer itemServer) {
		// CSVファイルを読み込むために FileInputStream を作成
		FileInputStream fis = null;
		Scanner scanner = null;
		try {
			// 指定されたCSVファイルのFileInputStreamを作成
			fis = new FileInputStream(getFilePath(ItemFinal.FILE_CSV, itemServer));
			// Scanner を使ってファイルを読み込む
			scanner = new Scanner(fis);
			// 区切り文字をカンマと改行に設定
			scanner.useDelimiter(ItemFinal.CUMMA_NEWLINE);

			// 1行目（ヘッダー）をスキップ
			if (scanner.hasNextLine()) {
				scanner.nextLine();
			}
			// ファイルの内容を読み込む
			while (scanner.hasNext()) {
				// ItemCsv オブジェクトを作成
				ItemCsv itemCsv = new ItemCsv();
				// 日付をパースして設定
				LocalDate localdate = LocalDate.parse(scanner.next(),
						DateTimeFormatter.ofPattern(ItemFinal.DATE_FORMAT));
				itemCsv.setDate(localdate);
				itemCsv.setName(scanner.next()); // 名前を設定
				itemCsv.setTime(scanner.next()); // 時間を設定
				// ボタンカラーをパースして設定
				ButtonColor tmpButtonColor = ButtonColor.fromCode(scanner.next());
				itemCsv.setButtoncolorEnum(tmpButtonColor);

				// itemServer の itemlist に追加
				itemServer.itemlist.put(localdate, itemCsv);
			}
		} catch (Exception e) {
			// スタックトレースを(error.log)に出力しダイアログ表示
			try {
				e.printStackTrace(new PrintStream(new FileOutputStream(ItemFinal.FILE_LOG)));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			exceptionDialogComponentSetting(itemServer, e);
		} finally {
			if (scanner != null) {
				try {
					scanner.close();
				} catch (Exception e2) {
					// closeメソッドが
					// IOExceptionを送出したとき
				}
			}
		}
	}

	/**
	 * CSVファイル(Item.csv)書込関数
	 * @param path ファイルのパス
	 * @param itemServer ItemServerオブジェクト
	 */
	public static void writeCSVFile(String path, ItemServer itemServer) {
		Writer out = null;
		try {
			// 指定されたパスにファイルライターを作成し、
			// バッファーライターでラップ
			out = new BufferedWriter(new FileWriter(path));
			// itemServer のローカルロケールXMLの各フィールドを
			// CSVフォーマットで書き込む
			writeCSVTitle(out, itemServer);

			// 各アイテムのフィールドをCSVフォーマットで書き込む
			writeCSVItemList(out, itemServer.getItemlist(), itemServer);

		} catch (Exception e) {
			// スタックトレースを(error.log)に出力しダイアログ表示
			try {
				e.printStackTrace(new PrintStream(new FileOutputStream(ItemFinal.FILE_LOG)));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			exceptionDialogComponentSetting(itemServer, e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e2) {
					// closeメソッドが
					// IOExceptionを送出したとき
				}
			}
		}
	}

	/**
	* CSVファイル(Item.csv)タイトル行作成処理
	*/
	private static void writeCSVTitle(Writer out, ItemServer itemServer) {
		try {
			// LocallocaleXmlオブジェクトを取得
			final LocallocaleXml title = itemServer.getLocallocaleXml();

			// CSVファイルにタイトル行を書き込む
			out.write(title.getDate() + ItemFinal.COMMA);
			out.write(title.getName() + ItemFinal.COMMA);
			out.write(title.getTime() + ItemFinal.COMMA);
			out.write(title.getFinishdate() + ItemFinal.COMMA);
			out.write(title.getButtoncolorEnum() + ItemFinal.NEWLINE);

		} catch (Exception e) {
			// スタックトレースを(error.log)に出力しダイアログ表示
			try {
				e.printStackTrace(new PrintStream(new FileOutputStream(ItemFinal.FILE_LOG)));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			exceptionDialogComponentSetting(itemServer, e);
		}
	}

	/**
	* CSVファイル(Item.csv)データ行作成処理
	*/
	private static void writeCSVItemList(Writer out, Map<LocalDate, ItemCsv> itemList, ItemServer itemServer) {
		try {
			// アイテムリストの各エントリに対して処理を行う
			for (ItemCsv item : itemList.values()) {
				LocalDate date = item.getDate();
				// 各フィールドの値をCSVに書き込む
				out.write(date.toString() + ItemFinal.COMMA);
				out.write(item.getName() + ItemFinal.COMMA);
				out.write(item.getTime() + ItemFinal.COMMA);
				out.write(item.getButtoncolorEnum().toString() + ItemFinal.NEWLINE);
			}
		} catch (Exception e) {
			// スタックトレースを(error.log)に出力しダイアログ表示
			try {
				e.printStackTrace(new PrintStream(new FileOutputStream(ItemFinal.FILE_LOG)));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			exceptionDialogComponentSetting(itemServer, e);
		}
	}

	/**
	* XMLファイル(Lang_xx.xml)の個別データ読込関数
	*/
	private static String readXMLFile(Element self, String name, ItemServer itemServer) {
		try {
			// 全ての子ノードを取得
			NodeList children = self.getChildNodes();
			// 子ノードの数だけループ
			for (int i = 0; i < children.getLength(); i++) {
				// 子ノードが Element インスタンスかどうかをチェック
				if (children.item(i) instanceof Element) {
					// Element にキャスト
					Element e = (Element) children.item(i);
					// タグ名が指定された名前と一致するかをチェック
					if (e.getTagName().equals(name)) {
						// 一致する場合、そのテキストコンテンツを返す
						return e.getTextContent();
					}
				}
			}
		} catch (Exception e) {
			// スタックトレースを(error.log)に出力しダイアログ表示
			try {
				e.printStackTrace(new PrintStream(new FileOutputStream(ItemFinal.FILE_LOG)));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			exceptionDialogComponentSetting(itemServer, e);
		}
		// 指定されたタグ名が存在しない場合、null を返す
		return null;
	}

	/**
	* XMLファイル(Lang_xx.xml)の全データ読込関数
	* @param itemServer ItemServerオブジェクト
	* 	*/
	public static void readAllXMLFiles(ItemServer itemServer) {
		// 言語ファイル(.xml)の読み込み
		InputStream is;
		try {
			if (itemServer.locale.getLanguage().equals(ItemFinal.LOCALE_JP)) {
				// 日本語ロケールの場合、日本語のXMLファイルを読み込む
				is = new FileInputStream(getFilePath(ItemFinal.FILE_XML_JP, itemServer));
			} else {
				// その他のロケールの場合、英語のXMLファイルを読み込む
				is = new FileInputStream(getFilePath(ItemFinal.FILE_XML_EN, itemServer));
			}

			// XMLファイルをパースしてドキュメントオブジェクトを作成
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);

			// ルート要素を取得
			Element Locale = doc.getDocumentElement();

			// 言語定義オブジェクトに全言語データを保存
			itemServer.setLocallocaleXml(new LocallocaleXml(readXMLFile(Locale, "frametitle", itemServer),
					readXMLFile(Locale, "lastmonth", itemServer),
					readXMLFile(Locale, "thismonth", itemServer),
					readXMLFile(Locale, "nextmonth", itemServer),
					readXMLFile(Locale, "save", itemServer),
					readXMLFile(Locale, "load", itemServer),
					readXMLFile(Locale, "sunday", itemServer),
					readXMLFile(Locale, "monday", itemServer),
					readXMLFile(Locale, "tuesday", itemServer),
					readXMLFile(Locale, "wednesday", itemServer),
					readXMLFile(Locale, "thursday", itemServer),
					readXMLFile(Locale, "friday", itemServer),
					readXMLFile(Locale, "saturday", itemServer),
					readXMLFile(Locale, "dialogtitle", itemServer),
					readXMLFile(Locale, "date", itemServer),
					readXMLFile(Locale, "name", itemServer),
					readXMLFile(Locale, "time", itemServer),
					readXMLFile(Locale, "hour", itemServer),
					readXMLFile(Locale, "min", itemServer),
					readXMLFile(Locale, "finishdate", itemServer),
					readXMLFile(Locale, "buttoncolorEnum", itemServer),
					readXMLFile(Locale, "alarm", itemServer),
					readXMLFile(Locale, "dialogdeletetitle", itemServer),
					readXMLFile(Locale, "delete", itemServer),
					readXMLFile(Locale, "dateformat", itemServer),
					readXMLFile(Locale, "dialogexceptiontitle", itemServer),
					readXMLFile(Locale, "exception1", itemServer),
					readXMLFile(Locale, "exception2", itemServer),
					readXMLFile(Locale, "exception3", itemServer),
					readXMLFile(Locale, "exception4", itemServer)));
		} catch (Exception e) {
			// スタックトレースを(error.log)に出力しダイアログ表示
			try {
				e.printStackTrace(new PrintStream(new FileOutputStream(ItemFinal.FILE_LOG)));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			exceptionDialogComponentSetting(itemServer, e);
		}
	}

	/**
	* プロパティファイル(Prm.ini)の読込関数
	* @param itemServer ItemServerオブジェクト
	*/
	public static void readPrmFile(ItemServer itemServer) {

		Reader fr;
		try {
			// 設定ファイルのパスを取得し、FileReader を作成
			fr = new FileReader(getFilePath(ItemFinal.FILE_INI, itemServer));

			// Properties オブジェクトを作成し、ファイルの内容を読み込む
			Properties p = new Properties();
			p.load(fr);

			// プロパティ "locale" を取得し、それに応じてロケールを設定
			String objlocale = p.getProperty("locale");
			if (objlocale.equals(ItemFinal.LOCALE_US)) {

				// ロケールが「LOCALE_US」の場合
				itemServer.locale = Locale.US;
			} else {
				// ロケールが「LOCALE_JAPAN」の場合
				itemServer.locale = Locale.JAPAN;
			}

			// プロパティ "windowsize" を取得し、
			// それに応じてウィンドウサイズとフォントサイズを設定
			String objwindowsize = p.getProperty("windowsize");
			if (objwindowsize.equals(ItemFinal.LARGE)) {

				// ウィンドウサイズが「LARGE」の場合
				itemServer.windowSizeX = 65;
				itemServer.windowSizeY = 105;
				itemServer.fontSizeS = 12;
				itemServer.fontSizeL = 17;
			} else if (objwindowsize.equals(ItemFinal.MIDDLE)) {

				// ウィンドウサイズが「MIDDLE」の場合
				itemServer.windowSizeX = 52;
				itemServer.windowSizeY = 84;
				itemServer.fontSizeS = 10;
				itemServer.fontSizeL = 15;
			} else {

				// ウィンドウサイズが「SMALL」の場合
				itemServer.windowSizeX = 43;
				itemServer.windowSizeY = 70;
				itemServer.fontSizeS = 8;
				itemServer.fontSizeL = 13;
			}
		} catch (Exception e) {
			// スタックトレースを(error.log)に出力しダイアログ表示
			try {
				e.printStackTrace(new PrintStream(new FileOutputStream(ItemFinal.FILE_LOG)));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			exceptionDialogComponentSetting(itemServer, e);
		}
	}

	/**
	* 全Swingコンポーネントの
	* デフォルトフォント設定関数
	* @param f フォント種別
	* @param itemServer ItemServerオブジェクト
	*/
	public static void setUIFont(javax.swing.plaf.FontUIResource f, ItemServer itemServer) {
		try {
			// UIManager のデフォルトテーブルのすべてのキーを取得
			java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();

			// すべてのキーを順に処理
			while (keys.hasMoreElements()) {

				// キーに対応する値を取得
				Object key = keys.nextElement();
				Object value = UIManager.get(key);

				// 値が FontUIResource のインスタンスであるかどうかをチェック
				if (value instanceof javax.swing.plaf.FontUIResource) {
					// デフォルトのフォントを指定されたフォントに置き換える
					UIManager.put(key, f);
				}
			}
		} catch (Exception e) {
			// スタックトレースを(error.log)に出力しダイアログ表示
			try {
				e.printStackTrace(new PrintStream(new FileOutputStream(ItemFinal.FILE_LOG)));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			exceptionDialogComponentSetting(itemServer, e);
		}
	}

	/** 
	* 例外ダイアログのコンポーネント設定関数
	* @param itemServer ItemServerオブジェクト
	* @param e 例外内容
	*/
	public static void exceptionDialogComponentSetting(ItemServer itemServer, Exception e) {

		// 例外メッセージを表示するJLabelを作成
		JLabel label = new JLabel("<html>"
				+ itemServer.getLocallocaleXml().getException1() // 例外メッセージの一部を取得
				+ "<br>"
				+ itemServer.getLocallocaleXml().getException2() // 例外メッセージの一部を取得
				+ e.getClass().getSimpleName() // 例外のクラス名を表示
				+ "<br>"
				+ "<br>"
				+ itemServer.getLocallocaleXml().getException3() // 例外メッセージの一部を取得
				+ "<br>"
				+ itemServer.getLocallocaleXml().getException4() // 例外メッセージの一部を取得
				+ "</html>", JLabel.CENTER);

		// JLabelのサイズを設定
		label.setPreferredSize(new Dimension(300, 43));

		// カスタムダイアログを作成し、表示する
		JCustomDialog dialog = new JCustomDialog(itemServer.getFrame(),
				itemServer.getLocallocaleXml().getDialogexceptiontitle(), label);
		dialog.setVisible(true);
	}

	/**
	 * ItemServerクラス
	 * 内部データを管理するためのクラス
	 */
	public class ItemServer {
		private Locale locale = Locale.JAPAN; // ロケール情報
		private int windowSizeX = 0; // ウィンドウx座標
		private int windowSizeY = 0; // ウィンドウy座標
		private int fontSizeS = 0; // 文字サイズ小
		private int fontSizeL = 0; // 文字サイズ大
		private LocallocaleXml locallocaleXml; // 言語定義(.xml)
		private LocalDate date = LocalDate.now(); // 日付定義
		private JCustomFrame frame; // フレーム定義
		private JPanel panel = new JPanel(); // パネル定義
		private JCustomlabel label; // ラベル定義
		private String mMonth = null; // 月表示定義
		private Map<LocalDate, ItemCsv> itemlist = new TreeMap<>(); // 予定リスト定義
		private JCustomButton objButton; // ボタン定義
		private List<JCustomButton> lstButtons = new ArrayList<>();// ボタンリスト定義
		private boolean doubleflag = false; // ホイール二重入力回避フラグ

		/**
		* デフォルトコンストラクタ。
		* ItemServer クラスの新しいインスタンスを作成します。
		*/
		public ItemServer() {
		}

		/**
		 * ロケール情報を取得
		 * @return locale ロケール情報
		 */
		public Locale getLocal() {
			return locale;
		}

		/**
		* ウィンドウx座標を取得
		* @return windowSizeX ウィンドウx座標
		*/
		public int getWindowSizeX() {
			return windowSizeX;
		}

		/**
		 * ウィンドウy座標を取得
		 * @return windowSizeY ウィンドウy座標
		 */
		public int getWindowSizeY() {
			return windowSizeY;
		}

		/**
		 * 文字サイズを取得
		 * @return fontSizeS 文字サイズ
		 */
		public int getFontSizeS() {
			return fontSizeS;
		}

		/**
		 * 文字サイズ(月表示用)を取得
		 * @return fontSizeL 文字サイズ(月表示用)
		 */
		public int getFontSizeL() {
			return fontSizeL;
		}

		/**
		 * 言語定義(.xml)を取得
		 * @return locallocaleXml 言語定義(.xml)
		 */
		public LocallocaleXml getLocallocaleXml() {
			return locallocaleXml;
		}

		/**
		 * 日付定義を取得
		 * @return date 日付定義
		 */
		public LocalDate getDate() {
			return date;
		}

		/**
		 * フレーム定義を取得
		 * @return frame フレーム定義
		 */
		public JCustomFrame getFrame() {
			return frame;
		}

		/**
		 * 月情報を取得
		 * @return mMonth 月情報
		 */
		public String getmMonth() {
			return mMonth;
		}

		/**
		 * 予定リスト定義を取得
		 * @return itemlist 予定リスト定義
		 */
		public Map<LocalDate, ItemCsv> getItemlist() {
			return itemlist;
		}

		/**
		 * ホイール二重入力回避フラグを取得
		 * @return doubleflag ホイール二重入力回避フラグ
		 */
		public boolean getDoubleflag() {
			return doubleflag;
		}

		/**
		 * 日付定義を設定
		 * @param date 日付定義
		 */
		public void setDate(LocalDate date) {
			this.date = date;
		}

		/**
		 * 言語定義(.xml)を設定
		 * @param locallocaleXml 言語定義(.xml)
		 */
		public void setLocallocaleXml(LocallocaleXml locallocaleXml) {
			this.locallocaleXml = locallocaleXml;
		}

		/**
		 * 月表示定義を設定
		 * @param mMonth 月表示定義
		 */
		public void setmMonth(String mMonth) {
			this.mMonth = mMonth;
		}

		/**
		 * 予定リスト定義に予定を追加
		 * @param date 予定日付
		 * @param itemCsv 予定リスト定義
		 */
		public void setItemlist(LocalDate date, ItemCsv itemCsv) {
			Map<LocalDate, ItemCsv> objItemlist = this.itemlist;
			objItemlist.put(date, itemCsv);
			this.itemlist = objItemlist;
		}

		/**
		 * 予定リスト定義から予定を削除
		 * @param date 予定日付
		 */
		public void removeItemlist(LocalDate date) {
			Map<LocalDate, ItemCsv> objItemlist = this.itemlist;
			objItemlist.remove(date);
			this.itemlist = objItemlist;
		}

		/**
		 * ホイール二重入力回避フラグを設定
		 * @param doubleflag ホイール二重入力回避フラグ
		 */
		public void setDoubleflag(boolean doubleflag) {
			this.doubleflag = doubleflag;
		}
	}
}
