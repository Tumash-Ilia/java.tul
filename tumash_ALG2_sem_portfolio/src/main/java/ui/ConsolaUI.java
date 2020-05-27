package ui;

import app.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * UI aplikace
 */
public class ConsolaUI {

    public static Scanner sc = new Scanner(System.in);
    private ComputingInfo compInf;
    private User user = new User();

    public void startProgram() throws IOException {
        menuStart();
        menuMain();
    }

    /**
     * Menu pro vytvoření uživatele nebo přihlášení do systemu
     */
    private void menuStart() {
        File filePath = new File("data");
        if (filePath.isDirectory()) {
            if (new File(filePath + "\\userbackup.txt").exists()) {
                System.out.println("\nByl nalezen vytvovoreny uzivatel:");
                System.out.print("Chcete pokracovat jako ");
                try (FileInputStream fin = new FileInputStream(filePath + "\\userbackup.txt")) {
                    int i = -1;
                    while ((i = fin.read()) != -1) {
                        System.out.print((char) i);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("?(ano/ne)");
                String answer = inputString();
                if (answer.equalsIgnoreCase("ano")) {
                    user.createUserFromSave();
                    user.createWalletFromSave();
                    System.out.println("Dekujeme, informace o uzivatelu byla uspesne aktualizovana!");
                } else {
                    createUser();
                    user.makeBackupUser();
                }
            } else {
                createUser();
                user.makeBackupUser();
            }
        }
    }

    /**
     * Hlavní menu
     *
     * @throws IOException
     */
    private void menuMain() throws IOException {
        boolean end = false;
        while (!end) {
            System.out.println("\nVitejte, " + user.getName() + " " + user.getSurname() + "!");
            System.out.println("1. Sprava uctu");
            System.out.println("2. Investicni tabulka");
            System.out.println("3. Informace o burzovnich obchodech");
            System.out.println("0. Vychod");
            System.out.println("Zvolte jednu možnost:");
            int answer = inputInt();
            switch (answer) {
                case 1:
                    menuWallet();
                    break;
                case 2:
                    menuTable();
                    break;
                case 3:
                    menuDeals();
                    break;
                case 0:
                    end = true;
                    break;
                default:
                    System.out.println("Funkce neexistuje, zkuste to znovu");
                    menuMain();
                    break;
            }
        }
    }

    /**
     * Menu pro správu účtů
     *
     * @throws IOException
     */
    private void menuWallet() throws IOException {
        boolean end = false;
        while (!end) {
            System.out.println("\n****Zde muzete spravovat sve ucty****");
            System.out.println("1. Zalozit novy ucet");
            System.out.println("2. Ulozit penize na ucet");
            System.out.println("3. Vyber penez z uctu");
            System.out.println("4. Prevod penez mezi ucty");
            System.out.println("5. Zmenit nazev uctu");
            System.out.println("6. Smazat ucet");
            System.out.println("7. Smazat vsechny ucty");
            System.out.println("8. Vypsat informace do souboru");
            System.out.println("9. Vypsat stav uctu");
            System.out.println("0. Vychod");
            System.out.println("Zvolte jednu možnost:");
            int answer = inputInt();
            user.addProfit();
            switch (answer) {
                case 1:
                    System.out.println("Zadejte nazev vaseho uctu:");
                    String nazev = inputString();
                    System.out.println("Zadejte pocatectny bilance:");
                    double bal = inputDouble();
                    user.addWallet(nazev, bal);
                    WalletFunctions.makeBackupCopy();
                    break;
                case 2:
                    System.out.println(WalletFunctions.showWallets());
                    System.out.println("Na jaky ucet ulozit penize?");
                    int index = inputInt();
                    System.out.println("Zadejte castku:");
                    double amount = inputDouble();
                    user.makeDeposit(index - 1, amount);
                    WalletFunctions.makeBackupCopy();
                    break;
                case 3:
                    System.out.println(WalletFunctions.showWallets());
                    System.out.println("Z jakeho uctu vabrat penize?");
                    int indexv = inputInt();
                    System.out.println("Zadejte castku:");
                    double amountv = inputDouble();
                    user.makeWithdraw(indexv - 1, amountv);
                    WalletFunctions.makeBackupCopy();
                    break;
                case 4:
                    System.out.println(WalletFunctions.showWallets());
                    System.out.println("Na jaky ucet prevest penize?");
                    int indext = inputInt();
                    System.out.println("Zadejte castku:");
                    double amountt = inputDouble();
                    user.transferMoney(indext - 1, amountt);
                    WalletFunctions.makeBackupCopy();
                    break;
                case 5:
                    System.out.println(WalletFunctions.showWallets());
                    System.out.println("Nazev jakeho uctu chcete zmenit?");
                    int indexz = inputInt();
                    System.out.println("Zadejte novy nazev:");
                    String novyn = inputString();
                    user.renameWallet(indexz - 1, novyn);
                    WalletFunctions.makeBackupCopy();
                    break;
                case 6:
                    System.out.println(WalletFunctions.showWallets());
                    System.out.println("Jaky ucet chcete smazat?");
                    int indexs = inputInt();
                    user.deleteWallet(indexs - 1);
                    WalletFunctions.makeBackupCopy();
                    break;
                case 7:
                    user.deleteAllWallets();
                    WalletFunctions.makeBackupCopy();
                    break;
                case 8:
                    WalletFunctions.saveWalletsInfo();
                    WalletFunctions.makeBackupCopy();
                    break;
                case 9:
                    System.out.println("Radit dle\n1. Nazvu\n2. Zustatku na uctu");
                    int vyber = inputInt();
                    if (vyber == 1) {
                        user.sortWalletsByName();
                        System.out.println(WalletFunctions.showWallets());
                    } else if (vyber == 2) {
                        user.sortWalletsByAmount();
                        System.out.println(WalletFunctions.showWallets());
                    } else {
                        System.out.println("Chybna volba");
                    }
                    WalletFunctions.makeBackupCopy();
                    break;
                case 0:
                    end = true;
                    break;
                default:
                    System.out.println("Funkce neexistuje, zkuste to znovu");
                    menuWallet();
                    break;
            }
        }
    }

    /**
     * Menu pro správu tabulky
     *
     * @throws IOException
     */
    private void menuTable() throws IOException {
        boolean end = false;
        while (!end) {
            System.out.println("\n****Zde muzete spravovat tabulku****");
            System.out.println("1. Vytvorit novou tabulku");
            System.out.println("2. Pridat novy radek");
            System.out.println("3. Vypsat tabulku");
            System.out.println("4. Smazat radek");
            System.out.println("5. Smazat tabulku");
            System.out.println("0. Vychod");
            int answer = inputInt();
            switch (answer) {
                case 1:
                    XlsxCreator.createTableFile();
                    System.out.println("Zadejte nazev tabulky:");
                    String nazev = inputString();
                    XlsxCreator.createTable(nazev);
                    break;
                case 2:
                    System.out.println("Zadejte nazev kryptomeny:");
                    String coin = inputString();
                    System.out.println("Vyberte pozice 1. Short 2. Long:");
                    int pos = inputInt();
                    System.out.println("Vyberte burzu 1. BitMEX 2. Binance:");
                    int exch = inputInt();
                    System.out.println("Zadejte min cenu:");
                    double min = inputDouble();
                    System.out.println("Zadejte max cenu:");
                    double max = inputDouble();
                    System.out.println("Zadejte leverage:");
                    int lev = inputInt();
                    System.out.println("Vyberete ucet pro transakci:");
                    System.out.println(WalletFunctions.showWallets());
                    int wallet = inputInt();
                    String wall = user.getWallets().get(wallet).getWalletName();
                    XlsxEditor.addNewRow(coin, pos, exch, min, max, lev, wall);
                    break;
                case 3:
                    XlsxEditor.printTable();
                    break;
                case 4:
                    System.out.println("Zadejte index radku, ktery chcete smazat:");
                    int ind = inputInt();
                    XlsxEditor.deleteRow(ind);
                    break;
                case 5:
                    XlsxCreator.deleteTable();
                    break;
                case 0:
                    end = true;
                    break;
                default:
                    System.out.println("Funkce neexistuje, zkuste to znovu");
                    menuTable();
                    break;
            }
        }
    }

    /**
     * Menu pro výpis informaci o burzovnich obchodech
     *
     * @throws IOException
     */
    private void menuDeals() throws IOException {
        ComputingInfo cu = new ComputingInfo();
        boolean end = false;
        while (!end) {
            System.out.println("\n****Zde muzete vypsat informaci o burzovnich obchodech****");
            System.out.println("1. Celkovy pocet obchodu");
            System.out.println("2. Pocet pozitivnich obchodu");
            System.out.println("3. Pocet negativnich obchodu");
            System.out.println("4. Celkovy vynos");
            System.out.println("5. Celkovy ubytek");
            System.out.println("6. Cisty vynos");
            System.out.println("7. Prumerny vynos z jedneho obchodu");
            System.out.println("0. Vychod");
            int answer = inputInt();
            switch (answer) {
                case 1:
                    System.out.println("Celkovy pocet obchodu:");
                    System.out.println(cu.allDeals());
                    break;
                case 2:
                    System.out.println("Pocet pozitivnich obchodu:");
                    System.out.println(cu.successfulDeals());
                    break;
                case 3:
                    System.out.println("Pocet negativnich obchodu:");
                    System.out.println(cu.unsuccessfulDeals());
                    break;
                case 4:
                    System.out.println("Celkovy vynos:");
                    System.out.printf("%-5.2f%s%n", cu.allProfit(), "%");
                    break;
                case 5:
                    System.out.println("Celkovy ubytek:");
                    System.out.printf("%-5.2f%s%n", cu.allLoss(), "%");
                    break;
                case 6:
                    System.out.println("Cisty vynos:");
                    System.out.printf("%-5.2f%s%n", cu.netProfit(), "%");
                    break;
                case 7:
                    System.out.println("Prumerny vynos z jedneho obchodu:");
                    System.out.printf("%-5.2f%s%n", cu.averageProfit(), "%");
                    break;
                case 0:
                    end = true;
                    break;
                default:
                    System.out.println("Funkce neexistuje, zkuste to znovu");
                    menuDeals();
                    break;
            }
        }
    }

    /**
     * Vytvoreni noveho uzivatele
     */
    private void createUser() {
        System.out.println("\nVytvoreni noveho uzivatele:");
        System.out.println("Vase jmeno:");
        String name = inputString();
        System.out.println("Vase prijmeni:");
        String surname = inputString();
        User user = new User(name, surname);
        System.out.println("Dekujeme, uzivatel uspesne vytvoren!");
    }

    /**
     * Kontrola vstupu pro typ Integer, vím že potřebujeme jen kladně čísla
     *
     * @return
     */
    private int inputInt() {
        int number;
        while (true) {
            while (!sc.hasNextInt()) {
                System.out.println("Neni to cislo, zkuste znovu.");
                sc.next();
            }
            number = sc.nextInt();
            if (number >= 0) {
                break;
            } else {
                System.out.println("Cislo musi byt kladne, zkuste znovu.");
            }
        }
        return number;
    }

    /**
     * Kontrola vstupu pro typ Double, vím že potřebujeme jen kladně čísla
     *
     * @return
     */
    private double inputDouble() {
        double number;
        while (true) {
            while (!sc.hasNextDouble()) {
                System.out.println("Neni to cislo, zkuste znovu.");
                sc.next();
            }
            number = sc.nextDouble();
            if (number >= 0) {
                break;
            } else {
                System.out.println("Cislo musi byt kladne, zkuste znovu.");
            }
        }
        return number;
    }

    /**
     * Kontrola vstupu pro typ String, nemůže být prázdný, nemůže začínat číslem
     *
     * @return
     */
    private String inputString() {
        while (!sc.hasNext("[a-zA-Z]+[0-9]*")) {
            System.out.println("Retezec musi obsahovat pismena(muze obsahovat cisla), nemuze zacinat cislem.");
            sc.next();
        }
        String s = sc.next();
        return s;
    }
}
