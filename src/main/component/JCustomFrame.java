package main.component;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import main.item.ItemFinal;
import main.main.CalendarMain;
import main.main.CalendarMain.ItemServer;

public class JCustomFrame extends JFrame {

	/**
	 * メイン画面フレーム用コンストラクタ
	 * @param title フレームのタイトル
	 * @param itemServer フレームで使用するデータを保持するItemServerオブジェクト
	 */
	public JCustomFrame(String title, ItemServer itemServer) {
		super(title + ItemFinal.APPVER); // フレームのタイトルを設定
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // フレームを閉じるとアプリケーションも終了
		setLocation(5, 5); // フレームの初期位置を設定
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		addMouseWheelListener(e -> {
			if (itemServer.getDoubleflag() == false) { 
				// ホイール二重入力回避フラグがfalseの時のみ処理を実行
				if (e.getWheelRotation() < 0) {
					/**
					* マウスホイールを上に回したとき（前月に移動）
					*/
					itemServer.setDate(itemServer.getDate().plusMonths(ItemFinal.HOUR_PRV)); // 月を1つ減らす
					CalendarMain.otherMonth(itemServer); // 前月の情報を設定
					CalendarMain.calenderSetting(true, itemServer); // カレンダーを再設定
				} else {
					/**
					* マウスホイールを下に回したとき（次月に移動）
					*/
					itemServer.setDate(itemServer.getDate().plusMonths(ItemFinal.HOUR_NEXT)); // 月を1つ増やす
					CalendarMain.otherMonth(itemServer); // 次月の情報を設定
					CalendarMain.calenderSetting(true, itemServer); // カレンダーを再設定
				}
				itemServer.setDoubleflag(true); // ホイール二重入力回避フラグをtrueに設定
			} else {
				itemServer.setDoubleflag(false); // ホイール二重入力回避フラグをfalseにリセット
			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == ItemFinal.WHEEL_BUTTON) {
					/**
					* マウスの中央ボタンが押されたとき
					*/
					itemServer.setDate(LocalDate.now()); // 日付を現在の日付に設定
					CalendarMain.now(itemServer); // 現在月の情報を設定
					CalendarMain.calenderSetting(true, itemServer); // カレンダーを再設定
				}
			}
		});
		setResizable(false); // フレームサイズを固定
	}
}
