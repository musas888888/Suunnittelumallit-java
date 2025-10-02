package CalendarAdapter;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarToNewDateAdapter implements NewDateInterface {
    private final Calendar cal;

    public CalendarToNewDateAdapter() {
        // Start with today's date
        this.cal = new GregorianCalendar();
        // Optional: zero time-of-day fields for clean output
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }

    public CalendarToNewDateAdapter(int day, int month, int year) {
        this();
        setYear(year);
        setMonth(month);
        setDay(day);
    }

    @Override
    public void setDay(int day) {
        cal.set(Calendar.DAY_OF_MONTH, day);
    }

    @Override
    public void setMonth(int month) {
        // convert 1-12 -> 0-11
        cal.set(Calendar.MONTH, month - 1);
    }

    @Override
    public void setYear(int year) {
        cal.set(Calendar.YEAR, year);
    }

    @Override
    public int getDay() {
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public int getMonth() {
        // convert 0-11 -> 1-12
        return cal.get(Calendar.MONTH) + 1;
    }

    @Override
    public int getYear() {
        return cal.get(Calendar.YEAR);
    }

    @Override
    public void advanceDays(int days) {
        cal.add(Calendar.DAY_OF_MONTH, days);
    }

    // helper for printing
    public String format() {
        // zero-pad to DD.MM.YYYY
        return String.format("%02d.%02d.%04d", getDay(), getMonth(), getYear());
    }
}

