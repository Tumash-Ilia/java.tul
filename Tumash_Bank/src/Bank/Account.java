package Bank;

public class Account {
    private double money;

    public Account() {
    }

    public Account(double money) {
        this.money = money;
    }

    public void deposit(double sum) {
        money = money + sum;
    }

    public void withdraw(double sum) {
        money = money - sum;
    }

    public double getBalance() {
        return money;
    }

}
