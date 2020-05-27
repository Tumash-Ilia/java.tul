package app;

import utils.AmountComparator;
import utils.NameComparator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Popisuje uživatele a obsahuje metody pro správu jeho účtů
 */

public class User {
    private static ArrayList<Wallet> wallets = new ArrayList<>();
    private static String name;
    private static String surname;

    public static ArrayList<Wallet> getWallets() {
        return wallets;
    }

    public static String getName() {
        return name;
    }

    public static String getSurname() {
        return surname;
    }

    public User() {
    }

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    /**
     * Metoda pro přidání účtu uživateli
     *
     * @param walletName název účtu
     * @param amount     počáteční bilans účtu
     */
    public void addWallet(String walletName, double amount) {
        if (wallets.size() < 2) {
            wallets.add(new Wallet(walletName, amount));
            System.out.println("Ucet byl uspesne vytvoren");
        } else {
            System.out.println("Nelze vytvorit ucet, maximalny pocet uctu na osubu je 2");
        }
    }

    /**
     * Smazání jedneho účtu
     *
     * @param index index účtu, který bude smazán
     */
    public void deleteWallet(int index) {
        try {
            if (wallets.get(index).getBalance() > 0 && wallets.size() != 1) {
                double balance = wallets.get(index).getBalance();
                wallets.remove(index);
                wallets.get(index).deposit(balance);
            } else {
                wallets.remove(index);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Vybral jste neexistuici ucet");
        }
    }

    /**
     * Smazání všech účtu.
     */
    public void deleteAllWallets() {
        wallets.removeAll(wallets);
        System.out.println("Vsehny ucty smazany");
    }

    /**
     * Metoda počítá množství peněz na všech účtech
     *
     * @return množství peněz na všech účtech
     */
    public static double getAllBalances() {
        double balances = 0;
        for (Wallet wallet : wallets) {
            balances += wallet.getBalance();
        }
        return balances;
    }

    /**
     * Metoda pro ukládání peněz na účet
     *
     * @param index  index účtu pro vklad
     * @param amount částka pro vklad
     */
    public void makeDeposit(int index, double amount) {
        try {
            wallets.get(index).deposit(amount);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Vybral jste neexistuici ucet");
        }

    }


    /**
     * Metoda pro výběr peněz z účtu
     *
     * @param index  index účtu pro výběr
     * @param amount částka pro výběr
     */
    public void makeWithdraw(int index, double amount) {
        try {
            wallets.get(index).withdraw(amount);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Vybral jste neexistuici ucet");
        }
    }

    /**
     * Metoda pro přejmenování účtu
     *
     * @param index index účtu pro přejmenování
     * @param name  nový název účtu
     */

    public void renameWallet(int index, String name) {
        try {
            wallets.get(index).setWalletName(name);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Vybral jste neexistuici ucet");
        }

    }


    /**
     * Metoda pro převod peněz na jiný účet
     *
     * @param index  index účtu, na který bude převod
     * @param amount částka pro převod
     */
    public void transferMoney(int index, double amount) {
        if (wallets.size() != 2) {
            System.out.println("Mate jenom jeden ucet, nelze provest prevod");
            return;
        }
        if (index == 0) {
            wallets.get(1).withdraw(amount);
            wallets.get(0).deposit(amount);
        } else {
            wallets.get(0).withdraw(amount);
            wallets.get(1).deposit(amount);
        }
    }

    /**
     * Metoda vytváří účty podle uloženého souboru
     */
    public void createWalletFromSave() {
        File filePath = new File("data");
        List<Character> list = new ArrayList<>();
        if (filePath.isDirectory()) {
            try (FileInputStream fin = new FileInputStream(filePath + "\\walletBackup.txt")) {
                int i = -1;
                while ((i = fin.read()) != -1) {
                    list.add((char) i);
                }
            } catch (IOException e) {
                System.out.println("File walletBackup.txt neexistuje");
                return;
            }
        } else {
            System.out.println("Directory data neexistuje");
            return;
        }
        String string = "";
        for (Character ch : list) {
            string += ch;
        }
        String[] substrings = string.split(" ");
        if (wallets.size() != 0) {
            wallets.removeAll(wallets);
        }
        if (substrings.length != 0) {
            wallets.add(new Wallet(substrings[0], Double.parseDouble(substrings[1]), Double.parseDouble(substrings[2])));
        }
        if (substrings.length == 6) {
            wallets.add(new Wallet(substrings[3], Double.parseDouble(substrings[4]), Double.parseDouble(substrings[5])));
        }
    }

    /**
     * Metoda přepočítává zůstatky účtů s přihlédnutím k procentům získaných z transakcí z tabulky
     *
     * @throws IOException
     */
    public void addProfit(){
        ComputingInfo depos = new ComputingInfo();
        double a = wallets.get(0).getBalance();
        double b = wallets.get(1).getBalance();
        double profa = depos.depositProfit(wallets.get(0).getWalletName()) - wallets.get(0).getProfit();
        double profb = depos.depositProfit(wallets.get(1).getWalletName()) - wallets.get(1).getProfit();
        wallets.get(0).setProfit(depos.depositProfit(wallets.get(0).getWalletName()));
        wallets.get(1).setProfit(depos.depositProfit(wallets.get(1).getWalletName()));
        if (profa < 100 && profa > 0) {
            profa += 100;
        }
        if (profb < 100 && profb > 0) {
            profb += 100;
        }
        a = a * (profa / 100);
        b = b * (profb / 100);
        if (a != 0) {
            wallets.get(0).setBalance(a);
        }
        if (b != 0) {
            wallets.get(1).setBalance(b);
        }
    }

    /**
     * Metoda uloží data o uživateli
     */
    public void makeBackupUser() {
        File filePath = new File("data");
        File save;
        if (filePath.isDirectory()) {
            if (!(new File(filePath + "\\userbackup.txt").exists())) {
                save = new File(filePath + "\\userbackup.txt");
                try {
                    save.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            filePath.mkdir();
            save = new File(filePath + "\\userbackup.txt");
            try {
                save.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String saveFile = name + " " + surname;

        try (FileOutputStream fos = new FileOutputStream(filePath + "\\userbackup.txt")) {
            byte[] buffer = saveFile.getBytes();
            fos.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Načte uživatelská data uložená v souboru, vytvoří uživatele podle těch dát
     */
    public void createUserFromSave() {
        File filePath = new File("data");
        List<Character> list = new ArrayList<>();
        if (filePath.isDirectory()) {
            try (FileInputStream fin = new FileInputStream(filePath + "\\userbackup.txt")) {
                int i = -1;
                while ((i = fin.read()) != -1) {
                    list.add((char) i);
                }
            } catch (IOException e) {
                System.out.println("File userbackup.txt neexistuje");
            }
        } else {
            System.out.println("Directory data neexistuje");
            return;
        }
        String string = "";
        for (Character ch : list) {
            string += ch;
        }
        String[] substrings = string.split(" ");
        name = substrings[0];
        surname = substrings[1];
    }

    /**
     * Metoda třídí účty podle názvu v abecedním pořadí
     */
    public static void sortWalletsByAmount() {
        AmountComparator ac = new AmountComparator();
        wallets.sort(ac);
    }

    /**
     * Metoda třídí účty podle zůstatku na účtu od min do max
     */
    public static void sortWalletsByName() {
        NameComparator ac = new NameComparator();
        wallets.sort(ac);
    }
 }
