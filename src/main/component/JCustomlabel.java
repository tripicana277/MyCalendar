package main.component;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import main.item.ItemFinal;
import main.main.CalendarMain.ItemServer;

public class JCustomlabel extends JLabel {

	/**
	 * 月の表示用コンストラクタ
	 * @param title ラベルに表示するテキスト
	 * @param itemServer フォントサイズなどの設定を含むItemServerオブジェクト
	 */
	public JCustomlabel(String title, ItemServer itemServer) {
		super(title, JLabel.CENTER); // テキストを中央揃えで表示
		setFont(new Font(ItemFinal.FONT, Font.PLAIN, itemServer.getFontSizeL())); // フォント設定	
		setForeground(ItemFinal.CUSTOM_DARK_BROWN);// 文字色を茶色に設定
		setBackground(Color.WHITE); // 背景色を白に設定
		setOpaque(true); // 背景色を表示するために不透明に設定
	}

	/**
	 * 週の表示用コンストラクタ
	 * @param title ラベルに表示するテキスト
	 * @param layout ラベルの位置揃え（例：左寄せ、中央揃えなど）
	 * @param itemServer ロケール設定などを含むItemServerオブジェクト
	 */
	public JCustomlabel(String title, int layout, ItemServer itemServer) {
		super(title, layout); // テキストと位置揃えを設定
		if (title.toString().equals(itemServer.getLocallocaleXml().getSunday())) {
			// 日曜日の場合、文字色を赤に設定
			setForeground(ItemFinal.CUSTOM_DARK_RED);
		} else if (title.toString().equals(itemServer.getLocallocaleXml().getSaturday())) {
			// 土曜日の場合、文字色を青に設定
			setForeground(ItemFinal.CUSTOM_DARK_BLUE);
		} else {
			// 土,日以外の場合、文字色を茶色に設定
			setForeground(ItemFinal.CUSTOM_DARK_BROWN);
		}

		// 茶色で境界線を設定
		setBorder(new LineBorder(ItemFinal.CUSTOM_DARK_BROWN, 1));
	}
}
