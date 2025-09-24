package CalendarAdapter;

public class Main {
    public static void main(String[] args) {
        // Client talks only to NewDateInterface
        NewDateInterface date = new CalendarToNewDateAdapter(28, 2, 2024);
        System.out.println("Start: " + ((CalendarToNewDateAdapter) date).format());

        date.advanceDays(3); // leap-year test: 28 Feb + 3 = 02 Mar
        System.out.println("After +3 days: " + ((CalendarToNewDateAdapter) date).format());

        // Set a new date and advance across months/years
        date.setDay(31);
        date.setMonth(12);
        date.setYear(2025);
        System.out.println("Reset: " + ((CalendarToNewDateAdapter) date).format());

        date.advanceDays(5); // should roll into next year
        System.out.println("After +5 days: " + ((CalendarToNewDateAdapter) date).format());
    }
}

