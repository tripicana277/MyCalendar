package main.item;

import java.time.LocalDate;

/**
 * スケジュールデータ（Item.csv）を保持するクラス
 */
public class ItemCsv {
	private LocalDate date; // 作業日
	private String name; // 作業予定名称
	private String time; // 予定開始時間
	private ButtonColor buttoncolorEnum; // ボタン色定義

	/**
	 * ボタンの色を定義する列挙型
	 */
	public enum ButtonColor {
		RED("RED"), BLUE("BLUE"), GREEN("GREEN"), CYAN("CYAN"), ORANGE("ORANGE");

		private final String code;

		/**
		 * 列挙型のコンストラクタ
		 * @param code 色を表す文字列
		 */
		ButtonColor(String code) {
			this.code = code;
		}

		/**
		 * 文字列から対応する列挙型の値を取得するメソッド
		 * @param code 色を表す文字列
		 * @return ButtonColor列挙型の値
		 */
		public static ButtonColor fromCode(String code) {
			// 文字列→Enum変換処理
			for (ButtonColor color : values()) {
				if (color.code.equals(code)) {
					return color;
				}
			}
			return null;
		}

		@Override
		public String toString() {
			return name();
		}
	}

	/**
	 * デフォルトコンストラクタ
	 */
	public ItemCsv() {
	}

	/**
	 * 全てのフィールドを初期化するコンストラクタ
	 * @param date 作業日
	 * @param name 作業予定名称
	 * @param time 予定開始時間
	 * @param buttoncolorEnum ボタン色定義
	 */
	public ItemCsv(LocalDate date, String name, String time, ButtonColor buttoncolorEnum) {
		this.setDate(date);
		this.setName(name);
		this.setTime(time);
		this.setButtoncolorEnum(buttoncolorEnum);
	}

	/**
	 * @return name 作業予定名称を取得
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return date 作業日を取得
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * @return time 予定開始時間を取得
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @return buttoncolorEnum ボタン色定義を取得
	 */
	public ButtonColor getButtoncolorEnum() {
		return buttoncolorEnum;
	}

	/**
	 * @param name 作業予定名称を設定
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param date 作業日を設定
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * @param time 予定開始時間を設定
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @param buttoncolorEnum ボタン色定義を設定
	 */
	public void setButtoncolorEnum(ButtonColor buttoncolorEnum) {
		this.buttoncolorEnum = buttoncolorEnum;
	}
}
