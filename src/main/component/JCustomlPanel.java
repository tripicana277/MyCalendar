package main.component;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.item.ItemFinal;
import main.main.CalendarMain.ItemServer;

public class JCustomlPanel extends JPanel {

	/**
	 * 先月、今月、来月のボタンパネルを作成するコンストラクタ
	 * @param buttonPrev 先月ボタン
	 * @param buttonNow 今月ボタン
	 * @param buttonNext 来月ボタン
	 * @param label その他の表示用ラベル
	 */
	public JCustomlPanel(JCustomButton buttonPrev, JCustomButton buttonNow, JCustomButton buttonNext, JLabel label) {
		super(new GridLayout(1, 7)); // 1行7列のグリッドレイアウト
		add(buttonPrev); // 先月ボタンを追加
		add(buttonNow); // 今月ボタンを追加
		add(buttonNext); // 来月ボタンを追加
		add(label); // ラベルを追加
	}

	/**
	 * スケジュール設定ダイアログ用のテキストフィールドパネルを作成するコンストラクタ
	 * @param label テキストフィールドのラベル
	 * @param textField テキストフィールド
	 */
	public JCustomlPanel(JLabel label, JTextField textField) {
		label.setForeground(ItemFinal.CUSTOM_DARK_BROWN);// 文字色を茶色に設定
		add(label); // ラベルを追加
		add(textField); // テキストフィールドを追加
	}

	/**
	 * スケジュール設定ダイアログ用の時刻設定コンボボックスパネルを作成するコンストラクタ
	 * @param hourCombo 時間のコンボボックス
	 * @param minCombo 分のコンボボックス
	 * @param itemServer サーバーからロケール情報を取得
	 */
	public JCustomlPanel(JCustomComboBox hourCombo, JCustomComboBox minCombo, ItemServer itemServer) {
		JLabel labelTime = new JLabel(itemServer.getLocallocaleXml().getTime());
		labelTime.setForeground(ItemFinal.CUSTOM_DARK_BROWN);// 文字色を茶色に設定
		add(labelTime); // 時刻のラベルを追加
		add(hourCombo); // 開始時刻のコンボボックスを追加
		JLabel labelHour = new JLabel(itemServer.getLocallocaleXml().getHour());
		labelHour.setForeground(ItemFinal.CUSTOM_DARK_BROWN);// 文字色を茶色に設定		
		add(labelHour); // 時間のラベルを追加
		add(minCombo); // 終了時刻のコンボボックスを追加
		JLabel labelMin = new JLabel(itemServer.getLocallocaleXml().getMin());
		labelMin.setForeground(ItemFinal.CUSTOM_DARK_BROWN);// 文字色を茶色に設定		
		add(labelMin); // 分のラベルを追加
	}

	/**
	 * スケジュール設定ダイアログ用のラジオボタンパネルを作成するコンストラクタ
	 * @param radioButtonGroup ラジオボタングループ
	 * @param itemServer サーバーからロケール情報を取得
	 */
	public JCustomlPanel(JCustomRadioButtonGroup radioButtonGroup, ItemServer itemServer) {
		// ラジオボタンパネルを追加
		JLabel label = new JLabel(itemServer.getLocallocaleXml().getButtoncolorEnum());
		label.setForeground(ItemFinal.CUSTOM_DARK_BROWN);// 文字色を茶色に設定
		add(label); // ボタン色選択のラベルを追加
		add(radioButtonGroup.getPanel()); // ラジオボタングループを追加
	}

	/**
	 * スケジュール設定ダイアログ用の決定ボタンパネルを作成するコンストラクタ
	 * @param label 決定ボタンのラベル
	 * @param button 決定ボタン
	 */
	public JCustomlPanel(JLabel label, JCustomButton button) {
		super(new BorderLayout()); // ボーダーレイアウトを使用
		add(label, BorderLayout.WEST); // ラベルを左側に配置
		add(button, BorderLayout.EAST); // ボタンを右側に配置
	}

	/**
	 * スケジュール削除ダイアログ用のパネルを作成するコンストラクタ
	 * @param button 削除ボタン
	 */
	public JCustomlPanel(JCustomButton button) {
		super(new BorderLayout()); // ボーダーレイアウトを使用
		add(button, BorderLayout.EAST); // 削除ボタンを右側に配置
	}
}
