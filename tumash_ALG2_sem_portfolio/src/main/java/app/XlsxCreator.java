package app;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Třída pro vytvoření tabulky
 */
public class XlsxCreator {
    private static File filePath = new File("data");
    private static File file;

    /**
     * Metoda pro vytváření .xlsx tabulky
     */
    public static void createTableFile() {
        if (filePath.isDirectory()) {
            if (!(new File(filePath + "\\tabulka.xlsx").exists())) {
                file = new File(filePath + "\\tabulka.xlsx");
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            filePath.mkdir();
            file = new File(filePath + "\\tabulka.xlsx");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Metoda vytvoří první řádek tabulky s názvy sloupců
     *
     * @param sheetName název stránky v tabulce
     * @throws IOException
     */
    public static void createTable(String sheetName) throws IOException {
        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet(sheetName);
        Row row = sheet.createRow(0);
        Cell cell;

        // Coin
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("Coin");

        // Position
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Position");

        // Date
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Date");

        // Exchange
        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Exchange");

        // Min price
        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Min price");

        // Max price
        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Max price");

        // Leverage
        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("Leverage");

        // Profit, %
        cell = row.createCell(7, CellType.STRING);
        cell.setCellValue("Profit, %");

        // Wallet
        cell = row.createCell(8, CellType.STRING);
        cell.setCellValue("Wallet");

        sheet.autoSizeColumn(1);
        try {
            FileOutputStream out = new FileOutputStream(file.getAbsolutePath());
            book.write(out);
            book.close();
            out.close();
            System.out.println("Tabulka uspesne vytvorena!");
        } catch (NullPointerException e) {
            System.out.println("Tabulka uz existuje");
        }

    }

    /**
     * Metoda která smaží tabulku
     */
    public static void deleteTable() {
        try {
            file.delete();
        } catch (NullPointerException e) {
            System.out.println("Tabulka neexistuje");
            return;
        }
        System.out.println("Tubalka je smazana");
    }

//    public static void main(String[] args) throws IOException {
//        createTableFile();
//        createTable("Trades");
//
//        // SNACHALA SOZDAT FILE!!!
//    }

}
