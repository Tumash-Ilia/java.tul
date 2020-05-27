package utils;

import app.Wallet;

import java.util.Comparator;

/**
 * Porovnání podle názvu účtu
 */
public class NameComparator implements Comparator<Wallet> {
    @Override
    public int compare(Wallet o1, Wallet o2) {
        if ((o1.getWalletName().compareTo(o2.getWalletName())) == 0) {
            return 0;
        }
        if ((o1.getWalletName().compareTo(o2.getWalletName())) > 0) {
            return 1;
        } else {
            return -1;
        }
    }
}
