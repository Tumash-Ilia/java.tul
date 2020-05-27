package app;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Popisuje další funkce účtu
 */
public class WalletFunctions {

    /**
     * Metoda formátuje informace o účtech pro hezké zobrazení na obrazovce
     *
     * @return formatovny řetězec
     * @throws IOException
     */
    public static String showWallets() throws IOException {
        ArrayList<Wallet> wallets = User.getWallets();
        String name = User.getName();
        String surname = User.getSurname();

        StringBuilder string = new StringBuilder();
        string.append("\t");
        string.append(name).append(" ").append(surname).append(" wallets:\n");
        if (wallets.size() > 0) {
            string.append("1. ").append(wallets.get(0).toString()).append("\n");
            if (wallets.size() == 2) {
                string.append("2. ").append(wallets.get(1).toString()).append("\n");
            }
            String total = String.format("%11s%9.1f%s", "\tTotal amount:", User.getAllBalances(), "$");
            string.append(total);
        } else {
            string.append("Zadne penezenky neexistuji, vytvorte novou penezenku");
        }
        return string.toString();
    }

    /**
     * Metoda pro ukládání formátované informace stavu účtu do souboru
     */
    public static void saveWalletsInfo() {
        File filePath = new File("data");
        File file;
        if (filePath.isDirectory()) {
            if (!(new File(filePath + "\\wallets.txt").exists())) {
                file = new File(filePath + "\\wallets.txt");
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            filePath.mkdir();
            file = new File(filePath + "\\wallets.txt");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileOutputStream fos = new FileOutputStream(filePath + "\\wallets.txt")) {
            byte[] buffer = showWallets().getBytes();
            fos.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Metoda uloží údaje o účtech do souboru, potřebné pro snadné obnovení dat
     */
    public static void makeBackupCopy() {
        ArrayList<Wallet> wallets = User.getWallets();
        File filePath = new File("data");
        File save;
        if (filePath.isDirectory()) {
            if (!(new File(filePath + "\\walletBackup.txt").exists())) {
                save = new File(filePath + "\\walletBackup.txt");
                try {
                    save.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            filePath.mkdir();
            save = new File(filePath + "\\walletBackup.txt");
            try {
                save.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String saveFile = "";
        if (wallets.size() > 0) {
            String w1 = wallets.get(0).getWalletName() + " " + wallets.get(0).getBalance() + " " + wallets.get(0).getProfit();
            saveFile = saveFile + w1 + " ";
        }
        if (wallets.size() == 2) {
            String w2 = wallets.get(1).getWalletName() + " " + wallets.get(1).getBalance() + " " + wallets.get(1).getProfit();
            saveFile += w2;
        }
        try (FileOutputStream fos = new FileOutputStream(filePath + "\\walletBackup.txt")) {
            byte[] buffer = saveFile.getBytes();
            fos.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
