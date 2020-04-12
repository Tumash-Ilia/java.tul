package Bank;

import java.util.ArrayList;

public abstract class Client {
    private ArrayList<Account> accounts = new ArrayList<>();
    protected String name;

    public Client(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void newAccount(double sum) {
        accounts.add(new Account(sum));
    }

    public double accountSum(){
        double sum = 0;
        for (Account account : accounts){
            sum += account.getBalance();
        }
        return sum;
    }

    public abstract String ClientName();
}
