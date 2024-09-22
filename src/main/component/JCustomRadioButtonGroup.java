package main.component;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import main.item.ItemCsv.ButtonColor;
import main.item.ItemFinal;

public class JCustomRadioButtonGroup {
	private ButtonGroup buttonGroup; // ラジオボタンのグループ
	private JPanel panel; // ラジオボタンを配置するパネル
	private String buttoncolor = ButtonColor.RED.toString(); // 現在選択されているボタンの色（初期値は赤）

	class Inner {
		private String buttonLabels; // ボタンに表示するラベル
		private Color color; // ボタンの色

		public Inner(String buttonLabels, Color color) {
			this.buttonLabels = buttonLabels;
			this.color = color;
		}

		public String getbuttonLabels() {
			return this.buttonLabels;
		}

		public void setColor(Color color) {
			this.color = color;
		}
	};

	/**
	 * スケジュール設定ダイアログ用
	 * ラジオボタングループを初期化するコンストラクタ
	 */
	public JCustomRadioButtonGroup() {
		buttonGroup = new ButtonGroup(); // ボタングループを初期化
		panel = new JPanel(new FlowLayout()); // ラジオボタンを配置するパネルを初期化
		List<Inner> listButtons = List.of(
				new Inner(ButtonColor.RED.toString(), ItemFinal.CUSTOM_RED),
				new Inner(ButtonColor.BLUE.toString(), ItemFinal.CUSTOM_BLUE),
				new Inner(ButtonColor.GREEN.toString(), ItemFinal.CUSTOM_GREEN),
				new Inner(ButtonColor.CYAN.toString(), ItemFinal.CUSTOM_CYAN),
				new Inner(ButtonColor.ORANGE.toString(), ItemFinal.CUSTOM_DARK_ORANGE));

		for (Inner objButton : listButtons) {
			// ラジオボタンを作成し、初期選択状態を赤色に設定
			JRadioButton radioButton = new JRadioButton(
					objButton.buttonLabels,
					objButton.getbuttonLabels().equals(ButtonColor.RED.toString()));
			radioButton.setForeground(objButton.color); // ボタンの文字色を設定
			buttonGroup.add(radioButton); // ボタンをグループに追加
			panel.add(radioButton); // パネルにボタンを追加
			radioButton.addActionListener(e -> {
				// ボタンが選択された場合

				// 現在選択されているボタンの色を変更
				this.buttoncolor = objButton.buttonLabels.toString();
			});
		}
	}

	/**
	 * スケジュール設定ダイアログ用
	 * ラジオボタングループを表示するパネルを取得する関数
	 * @return ラジオボタングループを含むパネル
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * スケジュール設定ダイアログ用
	 * 現在選択されているラジオボタンの色を取得する関数
	 * @return 現在選択されているボタンの色を示す文字列
	 */
	public String getButtonColer() {
		return buttoncolor;
	}
}