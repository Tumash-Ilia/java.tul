package utils;

import app.Wallet;

import java.util.Comparator;

/**
 * Porovnání podle zůstatku na účtu
 */
public class AmountComparator implements Comparator<Wallet> {

    @Override
    public int compare(Wallet o1, Wallet o2) {
        if (o1.getBalance() == o2.getBalance()) {
            return 0;
        }
        if (o1.getBalance() > o2.getBalance()) {
            return 1;
        } else {
            return -1;
        }
    }
}
