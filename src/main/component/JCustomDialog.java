package main.component;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;

import main.item.ItemFinal;

public class JCustomDialog extends JDialog {

	/**
	 * スケジュール設定ダイアログを作成するコンストラクタ
	 * @param frame 親フレーム
	 * @param title ダイアログのタイトル
	 * @param modal モーダルの設定 (true でモーダル、false で非モーダル)
	 * @param panelTextField テキストフィールドパネル
	 * @param panelComboBox コンボボックスパネル
	 * @param panelRadioButton ラジオボタンパネル
	 */
	public JCustomDialog(JCustomFrame frame, String title, boolean modal, JCustomlPanel panelTextField,
			JCustomlPanel panelComboBox,
			JCustomlPanel panelRadioButton) {
		// 親フレーム、タイトル、モーダル設定を
		// スーパークラスに渡す
		super(frame, title, modal);
		setBounds(8, 310, 460, 160); // ダイアログの位置とサイズを設定
		setAlwaysOnTop(true); // 常に最前面に表示
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setResizable(false); // フレームサイズを固定
		add(panelTextField); // テキストフィールドパネルをダイアログに追加
		add(panelComboBox); // コンボボックスパネルをダイアログに追加
		add(panelRadioButton); // ラジオボタンパネルをダイアログに追加
	}

	/**
	 * スケジュール削除ダイアログを作成するコンストラクタ
	 * @param frame 親フレーム
	 * @param title ダイアログのタイトル
	 * @param modal モーダルの設定 (true でモーダル、false で非モーダル)
	 * @param label ダイアログに追加するラベル
	 */
	public JCustomDialog(JCustomFrame frame, String title, boolean modal, JLabel label) {
		// 親フレーム、タイトル、モーダル設定を
		// スーパークラスに渡す
		super(frame, title, modal);
		setBounds(80, 320, 300, 100); // ダイアログの位置とサイズを設定
		setAlwaysOnTop(true); // 常に最前面に表示
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setResizable(false); // フレームサイズを固定
		label.setForeground(ItemFinal.CUSTOM_DARK_BROWN);// 文字色を茶色に設定		
		add(label); // ラベルをダイアログに追加
	}

	/**
	 * 例外ダイアログを作成するコンストラクタ
	 * @param frame 親フレーム
	 * @param title ダイアログのタイトル
	 * @param label ダイアログに追加するラベル
	 */
	public JCustomDialog(JCustomFrame frame, String title, JLabel label) {
		// 親フレーム、タイトル、モーダル設定を
		// スーパークラスに渡す
		super(frame, title, true);
		setBounds(80, 320, 300, 140); // ダイアログの位置とサイズを設定
		setAlwaysOnTop(true); // 常に最前面に表示
		setResizable(false); // フレームサイズを固定
		label.setForeground(ItemFinal.CUSTOM_DARK_BROWN);// 文字色を茶色に設定				
		add(label); // ラベルをダイアログに追加
	}
	
	/**
	 * ヘルプダイアログを作成するコンストラクタ
	 * @param frame 親フレーム
	 * @param title ダイアログのタイトル
	 * @param modal モーダルの設定 (true でモーダル、false で非モーダル)
	 * @param label ダイアログに追加するラベル
	 */
//	public JCustomDialog(JCustomFrame frame, String title, boolean modal, JLabel label) {
//		// 親フレーム、タイトル、モーダル設定を
//		// スーパークラスに渡す
//		super(frame, title, modal);
//		setBounds(80, 320, 300, 100); // ダイアログの位置とサイズを設定
//		setAlwaysOnTop(true); // 常に最前面に表示
//		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
//		setResizable(false); // フレームサイズを固定
//		label.setForeground(ItemFinal.CUSTOM_DARK_BROWN);// 文字色を茶色に設定		
//		add(label); // ラベルをダイアログに追加
//	}
}
