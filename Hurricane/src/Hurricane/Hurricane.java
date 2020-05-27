package Hurricane;

public class Hurricane {

    private int year;
    private String month;
    private int pressure;
    private int speed;
    private String name;

    public Hurricane(int year, String month, int pressure, int speed, String name) {
        this.year = year;
        this.month = month;
        this.pressure = pressure;
        this.speed = speed;
        this.name = name;
    }

    public double getSpeedInKmh() {
        return speed * (1.852);
    }

    public int getHurricaneCategory() {
        double speed = getSpeedInKmh();

        if (speed > 251) {
            return 5;
        } else if (speed > 208 && speed < 252) {
            return 4;
        } else if (speed > 177 && speed < 209) {
            return 3;
        } else if (speed > 153 && speed < 178) {
            return 2;
        } else if (speed > 118 && speed < 154) {
            return 1;
        } else {
            return 0;
        }
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return String.format("%-5d%-5s%-5d%-4d%-10s", year, month, pressure, speed, name);
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

}
