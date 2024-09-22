package main.item;

/**
 * 言語定義データ(Lang_xx.xml)取得用クラス
 */
public class LocallocaleXml {
	private String frametitle; // フレームのタイトル
	private String lastmonth; // 先月
	private String thismonth; // 今月
	private String nextmonth; // 来月
	private String save; // 保存
	private String load; // 読み込み
	private String sunday; // 日曜日
	private String monday; // 月曜日
	private String tuesday; // 火曜日
	private String wednesday; // 水曜日
	private String thursday; // 木曜日
	private String friday; // 金曜日
	private String saturday; // 土曜日
	private String dialogtitle; // ダイアログのタイトル
	private String date; // 作業日（または開始日）
	private String name; // 作業予定名称
	private String time; // 予定開始時間（xx:xx形式）
	private String hour; // 時
	private String min; // 分
	private String finishdate; // 作業終了日
	private String buttoncolorEnum; // 予定の表示色
	private String alarm; // アラーム
	private String dialogdeletetitle; // 削除ダイアログのタイトル
	private String delete; // 削除内容
	private String dateformat; // 日付フォーマット
	private String dialogexceptiontitle; // 例外ダイアログのタイトル
	private String exception1; // 例外内容1
	private String exception2; // 例外内容2
	private String exception3; // 例外内容3
	private String exception4; // 例外内容4

	/**
	 * コンストラクタ
	 * @param frametitle	フレームのタイトル
	 * @param lastmonth		先月
	 * @param thismonth		今月
	 * @param nextmonth		来月
	 * @param save			保存
	 * @param load			読み込み
	 * @param sunday		日曜日
	 * @param monday		月曜日
	 * @param tuesday		火曜日
	 * @param wednesday		水曜日
	 * @param thursday		木曜日
	 * @param friday		金曜日
	 * @param saturday		土曜日
	 * @param dialogtitle	ダイアログのタイトル
	 * @param date			作業日（または開始日）
	 * @param name			作業予定名称
	 * @param time			予定開始時間（xx:xx形式）
	 * @param hour			時
	 * @param min			分
	 * @param finishdate	作業終了日
	 * @param buttoncolorEnum 予定の表示色
	 * @param Alarm			アラーム
	 * @param dialogdeletetitle 削除ダイアログのタイトル
	 * @param delete		削除内容
	 * @param dateformat	日付フォーマット
	 * @param dialogexceptiontitle 	例外ダイアログ
	 * @param exception1	例外内容1
	 * @param exception2	例外内容2
	 * @param exception3	例外内容3
	 * @param exception4	例外内容4
	 */
	public LocallocaleXml(String frametitle,
			String lastmonth,
			String thismonth,
			String nextmonth,
			String save,
			String load,
			String sunday,
			String monday,
			String tuesday,
			String wednesday,
			String thursday,
			String friday,
			String saturday,
			String dialogtitle,
			String date,
			String name,
			String time,
			String hour,
			String min,
			String finishdate,
			String buttoncolorEnum,
			String Alarm,
			String dialogdeletetitle,
			String delete,
			String dateformat,
			String dialogexceptiontitle,
			String exception1,
			String exception2,
			String exception3,
			String exception4) {
		this.frametitle = frametitle;
		this.lastmonth = lastmonth;
		this.thismonth = thismonth;
		this.nextmonth = nextmonth;
		this.save = save;
		this.load = load;
		this.sunday = sunday;
		this.monday = monday;
		this.tuesday = tuesday;
		this.wednesday = wednesday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
		this.dialogtitle = dialogtitle;
		this.date = date;
		this.name = name;
		this.time = time;
		this.hour = hour;
		this.min = min;
		this.finishdate = finishdate;
		this.buttoncolorEnum = buttoncolorEnum;
		this.alarm = Alarm;
		this.dialogdeletetitle = dialogdeletetitle;
		this.delete = delete;
		this.dateformat = dateformat;
		this.dialogexceptiontitle = dialogexceptiontitle;
		this.exception1 = exception1;
		this.exception2 = exception2;
		this.exception3 = exception3;
		this.exception4 = exception4;
	}

	/**
	 * @return frametitle フレームのタイトルを取得
	 */
	public String getFrametitle() {
		return frametitle;
	}

	/**
	 * @return lastmonth 先月の文字列を取得
	 */
	public String getLastmonth() {
		return lastmonth;
	}

	/**
	 * @return thismonth 今月の文字列を取得
	 */
	public String getThismonth() {
		return thismonth;
	}

	/**
	 * @return nextmonth 来月の文字列を取得
	 */
	public String getNextmonth() {
		return nextmonth;
	}

	/**
	 * @return save 保存の文字列を取得
	 */
	public String getSave() {
		return save;
	}

	/**
	 * @return load 読み込みの文字列を取得
	 */
	public String getLoad() {
		return load;
	}

	/**
	 * @return sunday 日曜日の文字列を取得
	 */
	public String getSunday() {
		return sunday;
	}

	/**
	 * @return monday 月曜日の文字列を取得
	 */
	public String getMonday() {
		return monday;
	}

	/**
	 * @return tuesday 火曜日の文字列を取得
	 */
	public String getTuesday() {
		return tuesday;
	}

	/**
	 * @return wednesday 水曜日の文字列を取得
	 */
	public String getWednesday() {
		return wednesday;
	}

	/**
	 * @return thursday 木曜日の文字列を取得
	 */
	public String getThursday() {
		return thursday;
	}

	/**
	 * @return friday 金曜日の文字列を取得
	 */
	public String getFriday() {
		return friday;
	}

	/**
	 * @return saturday 土曜日の文字列を取得
	 */
	public String getSaturday() {
		return saturday;
	}

	/**
	 * @return dialogtitle ダイアログのタイトルを取得
	 */
	public String getDialogtitle() {
		return dialogtitle;
	}

	/**
	 * @return date 作業日（または開始日）を取得
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @return name 作業予定名称を取得
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return time 予定開始時間（xx:xx形式）を取得
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @return finishdate 作業終了日を取得
	 */
	public String getFinishdate() {
		return finishdate;
	}

	/**
	 * @return buttoncolorEnum 予定の表示色を取得
	 */
	public String getButtoncolorEnum() {
		return buttoncolorEnum;
	}

	/**
	 * @return hour 時を取得
	 */
	public String getHour() {
		return hour;
	}

	/**
	 * @return min 分を取得
	 */
	public String getMin() {
		return min;
	}

	/**
	 * @return dialogdeletetitle 削除ダイアログのタイトルを取得
	 */
	public String getDialogdeletetitle() {
		return dialogdeletetitle;
	}

	/**
	 * @return delete 削除内容を取得
	 */
	public String getDelete() {
		return delete;
	}

	/**
	 * @return alarm アラームの文字列を取得
	 */
	public String getAlarm() {
		return alarm;
	}

	/**
	 * @return dateformat 日付フォーマットを取得
	 */
	public String getDateformat() {
		return dateformat;
	}

	/**
	 * @return dialogexceptiontitle 例外ダイアログを取得
	 */
	public String getDialogexceptiontitle() {
		return dialogexceptiontitle;
	}

	/**
	 * @return exception1 例外内容1を取得
	 */
	public String getException1() {
		return exception1;
	}

	/**
	 * @return exception2 例外内容2を取得
	 */
	public String getException2() {
		return exception2;
	}

	/**
	 * @return exception3 例外内容3を取得
	 */
	public String getException3() {
		return exception3;
	}

	/**
	 * @return exception4 例外内容4を取得
	 */
	public String getException4() {
		return exception4;
	}
}
