package utility.setting;

import java.time.LocalTime;

/**
 * HourSetting is a specific kind of Setting.
 * An HourSetting has a category, taken from its parent class,
 * and it has a starting time and and ending time, defining a 
 * time range.
 * This can be used when the setting are based on time.
 */
public class HourSetting extends Setting {
	
	private final LocalTime fromTime;
	private final LocalTime toTime;
	
	/**
	 * Constructor of HourSetting.
	 * 
	 * @param category the category that the setting is going to regulate.
	 * @param fromTime the starting time of the setting.
	 * @param toTime   the end time of the setting.
	 */
	public HourSetting(final String category, final LocalTime fromTime, final LocalTime toTime) {
		super(category);
		this.fromTime = fromTime;
		this.toTime = toTime;
	}

	/**
	 * An HourSetting has an initial time, which is used to know
	 * when that specific setting has to be applied.
	 * 
	 * @return the starting time of the HourSetting.
	 */
	public LocalTime getFromTime() {
		return fromTime;
	}

	/**
	 * An HourSetting has an ending time, which is used
	 * to know when that specific setting has to 
	 * be stopped to be applied.
	 * 
	 * @return the ending time of the HourSetting.
	 */
	public LocalTime getToTime() {
		return toTime;
	}

	@Override
	public String toString() {
		return "HourSetting [fromTime=" + fromTime + ", toTime=" + toTime + " " + super.toString() +  "]";
	}

}
