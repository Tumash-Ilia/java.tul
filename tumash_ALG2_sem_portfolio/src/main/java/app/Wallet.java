package app;

/**
 * Poposuje učet
 */
public class Wallet {
    private double balance = 0;
    private String walletName;
    private double profit = 0;

    public Wallet(String walletName, double balance, double profit) {
        this.balance = balance;
        this.walletName = walletName;
        this.profit = profit;
    }

    public Wallet(String walletName, double balance) {
        this.walletName = walletName;
        this.balance = balance;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getProfit() {
        return profit;
    }

    /**
     * Metoda pro ukládání peněz na účet
     *
     * @param sum částka pro vklad
     */
    public void deposit(double sum) {
        balance = balance + sum;
    }

    /**
     * Metoda pro výběr peněz z účtu
     *
     * @param sum částka pro výběr
     */
    public void withdraw(double sum) {
        if (balance - sum > 0) {
            balance = balance - sum;
        } else {
            System.out.println("Maximalni vyber je " + getBalance());
        }
    }

    @Override
    public String toString() {
        String s = String.format("%-10s%-10s%6.1f%s", walletName, " value: ", balance, "$");
        return s;
    }
}
