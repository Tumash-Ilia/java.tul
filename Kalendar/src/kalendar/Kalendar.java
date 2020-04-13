package kalendar;

public class Kalendar {
    private int day;
    private int month;
    private int year;
    private static int[] daysInMonth = {31,28,31,30,31,30,31,31,30,31,30,31};

    public Kalendar(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int dayOfWeek() {
        int day = this.day;
        int month = this.month;
        int year = this.year;
        if (month == 1 || month == 2) {
            month += 12;
            year -= 1;
        }
        // day = q
        int h;
        int K = year % 100;
        int J = year / 100;
        int dayOfWeek;
        h = (day + (13 * (month + 1)) / 5  + K + (K / 4) + (J / 4) + 5 * J) % 7;

        dayOfWeek = ((h + 5) % 7) + 1;
        return dayOfWeek;
    }

    public void nextMonth(){
        this.day = 1;
        if (this.month != 12) {
            this.month += 1;
        }else {
            this.month = 1;
            this.year += 1;
        }

    }
    public void prevMonth(){
        this.day = 1;
        if (this.month != 1) {
            this.month -= 1;
        }else {
            this.month = 12;
            this.year -= 1;
        }

    }

    public String tableOfMonth(){
        return null;
    }

    public static boolean leapYear(int year){
        return  ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
    }
    public static int daysInYear(int year){
        int days = leapYear(year)? 366 : 365;
        return days;
    }
    public static void main(String[] args) {
        Kalendar date = new Kalendar(13, 4,2020);
        Kalendar date1 = new Kalendar(14, 4,2020);
        Kalendar date2 = new Kalendar(1, 5,2020);
        Kalendar date3 = new Kalendar(7, 3,2020);
        Kalendar date4 = new Kalendar(1, 1,2020);
        Kalendar date5 = new Kalendar(4, 2,2020);

        System.out.println(leapYear(2020));
        System.out.println(daysInYear(2020));
    }

}
