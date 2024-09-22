package main.component;

import java.awt.Color;
import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import main.item.ItemCsv;
import main.item.ItemCsv.ButtonColor;
import main.item.ItemFinal;
import main.main.CalendarMain;
import main.main.CalendarMain.ItemServer;

public class JCustomButton extends JButton {

	/**
	 * 月送りボタン用コンストラクタ
	 * @param title ボタンのタイトル
	 */
	public JCustomButton(String title) {
		super(title);
		setForeground(ItemFinal.CUSTOM_DARK_BROWN);// 文字色を茶色に設定
		setBackground(ItemFinal.CUSTOM_LIGHT_GRAY); // ボタンの背景色を設定
		setBorder(new LineBorder(ItemFinal.CUSTOM_DARK_BROWN, 1));
	}

	/**
	 * スケジュール設定ダイアログを表示するボタン用コンストラクタ
	 * @param title ボタンのタイトル
	 * @param dimension ボタンのサイズ
	 * @param itemServer ItemServer インスタンス
	 */
	public JCustomButton(String title, Dimension dimension, ItemServer itemServer) {
		super(title);
		setHorizontalAlignment(JButton.LEFT); // ボタンの水平位置を左に設定
		setVerticalAlignment(JButton.TOP); // ボタンの垂直位置を上に設定
		setPreferredSize(dimension); // ボタンのサイズを設定
		setForeground(ItemFinal.CUSTOM_DARK_BROWN);// 文字色を茶色に設定	
		addActionListener(e -> {
			if (!e.getActionCommand().isEmpty()) {
				// データが空でない場合

				// 日付設定関数の呼び出し
				LocalDate date = dateSetting(e.getActionCommand(), itemServer);
				if (!itemServer.getItemlist().containsKey(date) || itemServer.getItemlist().isEmpty()) {
					// 予定がない場合、スケジュール設定ダイアログを表示
					dialogComponentSetting(date, itemServer);
				} else {
					// 予定がある場合、スケジュール削除ダイアログを表示
					deleteDialogComponentSetting(date, itemServer);
				}
			}
		});
	}

	/**
	 * 日付設定関数
	 * @param command コマンド
	 * @param itemServer ItemServer インスタンス
	 * @return 設定した日付の LocalDate オブジェクト
	 */
	public LocalDate dateSetting(String command, ItemServer itemServer) {
		try {
			int dateNo;
			if (command.substring(0, 1).startsWith(ItemFinal.BRACKETS)) {
				// 文字列がHTML形式の場合
				if (command.substring(7, 8).startsWith(ItemFinal.BRACKETS)) {
					// 例: "[01]" 形式の場合
					dateNo = Integer.parseInt(command.substring(6, 7)) - 1;
				} else {
					// 例: "[10]" 形式の場合
					dateNo = Integer.parseInt(command.substring(6, 8)) - 1;
				}
			} else {
				// 数字のみの場合
				dateNo = Integer.parseInt(command) - 1;
			}
			Integer integerDate = Integer.valueOf(dateNo + 1);
			StringBuilder sb = new StringBuilder();
			sb.delete(0, sb.length()); // StringBuilder を初期化

			// java.time.LocalDateからStringに変換する
			sb.append(itemServer.getDate().format(DateTimeFormatter.ofPattern(ItemFinal.MONTH_FORMAT)));
			if ((dateNo + 1) < ItemFinal.TWO_DIG) {
				// 1桁の日付の場合、先頭にゼロを追加
				sb.append(ItemFinal.ZERO_PAD);
			}
			sb.append(integerDate.toString());
			// Stringからjava.time.LocalDateに変換する
			return LocalDate.parse(sb.toString(), DateTimeFormatter.ofPattern(ItemFinal.DATE_FORMAT));

		} catch (Exception e) {
			// スタックトレースを(error.log)に出力しダイアログ表示
			try {
				e.printStackTrace(new PrintStream(new FileOutputStream(ItemFinal.FILE_LOG)));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			CalendarMain.exceptionDialogComponentSetting(itemServer, e);
			return null;
		}
	}

	/**
	 * スケジュール設定ダイアログのコンポーネント設定関数
	 * @param testDate 設定する日付
	 * @param itemServer ItemServer インスタンス
	 */
	public void dialogComponentSetting(LocalDate testDate, ItemServer itemServer) {
		try {
			JTextField textField = new JTextField(); // 入力フィールドを作成
			textField.setForeground(ItemFinal.CUSTOM_DARK_BROWN);// 文字色を茶色に設定
			textField.setBorder(new LineBorder(ItemFinal.CUSTOM_DARK_BROWN, 1));

			JCustomlPanel panelTextField = new JCustomlPanel(new JLabel(itemServer.getLocallocaleXml().getName()),
					textField);
			panelTextField.setLayout(new BoxLayout(panelTextField, BoxLayout.X_AXIS));

			JCustomComboBox hourCombo = new JCustomComboBox(ItemFinal.HOUR); // 時間のコンボボックスを作成
			JCustomComboBox minCombo = new JCustomComboBox(ItemFinal.MIN); // 分のコンボボックスを作成
			JCustomlPanel panelComboBox = new JCustomlPanel(hourCombo, minCombo, itemServer);
			panelComboBox.setLayout(new BoxLayout(panelComboBox, BoxLayout.X_AXIS));

			// ラジオボタンのラベルをリストで指定
			JCustomRadioButtonGroup radioButtonGroup = new JCustomRadioButtonGroup();
			JCustomlPanel panelRadioButton = new JCustomlPanel(radioButtonGroup, itemServer);
			panelRadioButton.setLayout(new BoxLayout(panelRadioButton, BoxLayout.X_AXIS));

			// スケジュール設定ダイアログの作成
			JCustomDialog dialog = new JCustomDialog(itemServer.getFrame(),
					itemServer.getLocallocaleXml().getDialogtitle(), true, panelTextField, panelComboBox,
					panelRadioButton);
			JLabel label = new JLabel();
			JCustomButton button = new JCustomButton(new Dimension(175, 25), textField, radioButtonGroup,
					hourCombo, minCombo, testDate, dialog, label, itemServer);
			JCustomlPanel panelButton = new JCustomlPanel(label, button);
			dialog.add(panelButton); // ダイアログにパネルを追加
			dialog.setVisible(true); // ダイアログを表示

		} catch (Exception e) {
			// スタックトレースを(error.log)に出力しダイアログ表示
			try {
				e.printStackTrace(new PrintStream(new FileOutputStream(ItemFinal.FILE_LOG)));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			CalendarMain.exceptionDialogComponentSetting(itemServer, e);
		}
	}

	/**
	 * スケジュール削除ダイアログのコンポーネント設定関数
	 * @param testDate 削除する日付
	 * @param itemServer ItemServer インスタンス
	 */
	public void deleteDialogComponentSetting(LocalDate testDate, ItemServer itemServer) {
		JLabel label = new JLabel(itemServer.getLocallocaleXml().getDelete(), JLabel.CENTER);
		label.setPreferredSize(new Dimension(300, 43));
		JCustomDialog dialog = new JCustomDialog(itemServer.getFrame(),
				itemServer.getLocallocaleXml().getDialogdeletetitle(), true, label);
		JCustomButton button = new JCustomButton(new Dimension(130, 10), testDate, dialog, itemServer);
		JCustomlPanel panel = new JCustomlPanel(button);
		dialog.add(panel); // ダイアログにパネルを追加
		dialog.setVisible(true); // ダイアログを表示
	}

	/**
	 * スケジュール設定ダイアログの決定ボタン用コンストラクタ
	 * @param dimension ボタンのサイズ
	 * @param textField 入力フィールド
	 * @param radioButtonGroup ラジオボタングループ
	 * @param hourCombo 開始時間のコンボボックス
	 * @param minCombo 終了時間のコンボボックス
	 * @param testDate 設定する日付
	 * @param dialog ダイアログ
	 * @param label ラベル
	 * @param itemServer ItemServer インスタンス
	 */
	public JCustomButton(Dimension dimension, JTextField textField,
			JCustomRadioButtonGroup radioButtonGroup, JCustomComboBox hourCombo, JCustomComboBox minCombo,
			LocalDate testDate, JCustomDialog dialog, JLabel label, ItemServer itemServer) {
		super(ItemFinal.OK); // ボタンのタイトルを設定
		setPreferredSize(dimension); // ボタンのサイズを設定
		setForeground(ItemFinal.CUSTOM_DARK_BROWN);// 文字色を茶色に設定
		setBackground(Color.WHITE); // ボタンの背景色を設定
		setBorder(new LineBorder(ItemFinal.CUSTOM_DARK_BROWN, 1));
		addActionListener(e -> {
			try {
				if (!textField.getText().equals(ItemFinal.BLANK)) {
					// 入力フィールドが空でない場合

					// 時間表示の構築
					String time = (String) hourCombo.getSelectedItem() // 時間
							+ ItemFinal.COLON // コロン
							+ (String) minCombo.getSelectedItem() // 分
							+ ItemFinal.HYPHEN; // ハイフン
					ItemCsv itemCsv = new ItemCsv(testDate, textField.getText(), time,
							ButtonColor.fromCode(radioButtonGroup.getButtonColer()));
					itemServer.setItemlist(testDate, itemCsv); // ItemServer にアイテムを追加
					CalendarMain.writeCSVFile(ItemFinal.FILE_CSV, itemServer); // CSV ファイルに書き込み
					CalendarMain.calenderSetting(true, itemServer); // カレンダーを再設定
					dialog.setVisible(false); // ダイアログを非表示
				} else {
					// 入力フィールドが空の場合
					label.setText(itemServer.getLocallocaleXml().getAlarm());
					label.setForeground(ItemFinal.CUSTOM_DARK_RED); // 警告のためラベルの色を赤に設定
				}
			} catch (Exception e1) {
				// スタックトレースを(error.log)に出力しダイアログ表示
				try {
					e1.printStackTrace(new PrintStream(new FileOutputStream(ItemFinal.FILE_LOG)));
				} catch (FileNotFoundException e2) {
					e2.printStackTrace();
				}
				e1.printStackTrace();
				CalendarMain.exceptionDialogComponentSetting(itemServer, e1);
			}
		});
	}

	/**
	 * スケジュール削除ダイアログの決定ボタン用コンストラクタ
	 * @param dimension ボタンのサイズ
	 * @param testDate 削除する日付
	 * @param dialog ダイアログ
	 * @param itemServer ItemServer インスタンス
	 */
	public JCustomButton(Dimension dimension, LocalDate testDate, JDialog dialog, ItemServer itemServer) {
		super(ItemFinal.OK); // ボタンのタイトルを設定
		setPreferredSize(dimension); // ボタンのサイズを設定
		setForeground(ItemFinal.CUSTOM_DARK_BROWN);// 文字色を茶色に設定
		setBackground(Color.WHITE); // ボタンの背景色を設定
		setBorder(new LineBorder(ItemFinal.CUSTOM_DARK_BROWN, 1));
		addActionListener(e -> {
			try {
				itemServer.removeItemlist(testDate); // ItemServer からアイテムを削除
				CalendarMain.writeCSVFile(ItemFinal.FILE_CSV, itemServer); // CSV ファイルに書き込み
				CalendarMain.calenderSetting(true, itemServer); // カレンダーを再設定
				dialog.setVisible(false); // ダイアログを非表示

			} catch (Exception e1) {
				// スタックトレースを(error.log)に出力しダイアログ表示
				try {
					e1.printStackTrace(new PrintStream(new FileOutputStream(ItemFinal.FILE_LOG)));
				} catch (FileNotFoundException e2) {
					e2.printStackTrace();
				}
				e1.printStackTrace();
				CalendarMain.exceptionDialogComponentSetting(itemServer, e1);
			}
		});
	}
}
