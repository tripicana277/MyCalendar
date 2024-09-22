package main.component;

import java.awt.Color;

import javax.swing.JComboBox;

import main.item.ItemFinal;

public class JCustomComboBox extends JComboBox<String> {

	/**
	 * スケジュール設定ダイアログ用のコンボボックスを作成するコンストラクタ
	 * @param stringlist コンボボックスに表示する項目の配列
	 */
	public JCustomComboBox(String[] stringlist) {
		// スーパークラスのコンストラクタを呼び出し、
		// 項目の配列を設定
		super(stringlist);
		setBackground(Color.WHITE); // コンボボックスの背景色を白に設定
		setForeground(ItemFinal.CUSTOM_DARK_BROWN);// 文字色を茶色に設定
	}
}
