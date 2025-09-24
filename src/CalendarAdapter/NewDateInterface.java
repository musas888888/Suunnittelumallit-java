package CalendarAdapter;

public interface NewDateInterface {
    void setDay(int day);
    void setMonth(int month);   // 1-12
    void setYear(int year);

    int getDay();
    int getMonth();             // 1-12
    int getYear();

    void advanceDays(int days);
}

