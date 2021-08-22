package il.asulin.messageService.timer;

import java.util.concurrent.TimeUnit;

public interface TimeResolver {
	 /**
     * Method parse given string to custom time.
     * given String should be in the following format: <value><Unit>
     * Units:
     * M minutes
     * m milli
     * S/s seconds
     * H/h hours
     *
     * @param key       - Corresponded application properties key.
     * @param timeStr   - The value for specific key.
     * @return Custom time from String.
     */
    default CustomTime resolveTime(final String key, final String timeStr) {
        if (timeStr == null || timeStr.isEmpty()) {
            throw new IllegalArgumentException("Time is missing for key:" + key);
        }
        TimeUnit timeUnit = null;
        char unit = timeStr.charAt(timeStr.length() - 1);
        switch (unit) {
            case 'M':
            	timeUnit = TimeUnit.MINUTES;
                break;
            case 'm':
                timeUnit = TimeUnit.MILLISECONDS;
                break;
            case 'S':
            case 's':
                timeUnit = TimeUnit.SECONDS;
                break;
            case 'H':
            case 'h':
                timeUnit = TimeUnit.HOURS;
                break;
            default:
                throw new IllegalArgumentException("Unit not present in the required format for " + key + ", input:" + timeStr);
        }
        long time;
        try {
            time = Long.parseLong(timeStr.substring(0, timeStr.length() - 1));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Time could not be parsed for:" + key + ", input: " + timeStr, ex);
        }
        return new CustomTime(time, timeUnit);
    }
}
