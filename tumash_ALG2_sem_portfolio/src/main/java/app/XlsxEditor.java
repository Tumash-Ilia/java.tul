package app;


import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Třída pro úpravu tabulky
 */
public class XlsxEditor {
    private static File filePath = new File("data");
    private static File file = new File(filePath + "\\tabulka.xlsx");

    /**
     * Metoda která ukládá do Listu sloupec s procenty všech obchodů z tabulky
     *
     * @return List<Double> loupec s procenty všech obchodů
     * @throws IOException
     */
    public static List<Double> getProfitColumn() {
        List<Double> list = new ArrayList<>();
        try (FileInputStream is = new FileInputStream(file);) {
            Workbook book = new XSSFWorkbook(is);
            Sheet sheet = book.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Cell cell = row.getCell(7);
                CellType cellType = cell.getCellType();
                if (cellType.equals(CellType.FORMULA)) {
                    FormulaEvaluator evaluator = book.getCreationHelper().createFormulaEvaluator();
                    list.add(evaluator.evaluate(cell).getNumberValue());
                }
            }
            book.close();
        } catch (IOException e) {
            System.out.println("Tabulka neexistuje, vytvorte tubulku");
        }
        return list;

    }

    /**
     * Metoda která ukládá do Listu sloupec s procenty všech obchodů z tabulky, podle názvu účtu
     *
     * @param name název účtu
     * @return List<Double> sloupec s procenty všech obchodů
     * @throws IOException
     */
    public static List<Double> geWalletProfit(String name) {
        List<Double> list = new ArrayList<>();
        try (FileInputStream is = new FileInputStream(file)) {
            Workbook book = new XSSFWorkbook(is);
            Sheet sheet = book.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Cell cellWallet = row.getCell(8);
                Cell profit = row.getCell(7);
                if (cellWallet.getStringCellValue().equals(name)) {
                    list.add(profit.getNumericCellValue());
                }
            }
            book.close();
        } catch (IOException e) {
            System.out.println("Tabulka neexistuje, vytvorte tubulku");
        }
        return list;
    }

    /**
     * Metoda pro přidání nového řádku do tabulky
     *
     * @param coin     název kryptoměny
     * @param pos      výběr pozice 1. Short 2. Long
     * @param exchange výběr burzy 1. BitMEX 2. Binance
     * @param minPrice minimální cena
     * @param maxPrice maximální cena
     * @param leverage leverage
     * @param wallet   název účtu
     * @throws IOException
     */
    public static void addNewRow(String coin, int pos, int exchange, double minPrice, double maxPrice, int leverage, String wallet) {
        List<Double> list = new ArrayList<>();
        try (FileInputStream is = new FileInputStream(file)) {
            Workbook book = new XSSFWorkbook(is);
            Sheet sheet = book.getSheetAt(0);
            Row row = sheet.createRow(sheet.getPhysicalNumberOfRows());
            Cell cell;

            // Coin
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(coin);

            // Position
            cell = row.createCell(1, CellType.STRING);
            if (pos == 1) {
                cell.setCellValue("Short");
            } else {
                cell.setCellValue("Long");
            }

            // Date
            Cell date = row.createCell(2);
            DataFormat format = book.createDataFormat();
            CellStyle dateStyle = book.createCellStyle();
            dateStyle.setDataFormat(format.getFormat("dd.mm.yy"));
            date.setCellStyle(dateStyle);
            date.setCellValue(LocalDate.now());

            // Exchange
            cell = row.createCell(3, CellType.STRING);
            if (exchange == 1) {
                cell.setCellValue("BitMEX");
            } else {
                cell.setCellValue("Binance");
            }

            // Min price
            cell = row.createCell(4, CellType.NUMERIC);
            cell.setCellValue(minPrice);

            // Max price
            cell = row.createCell(5, CellType.NUMERIC);
            cell.setCellValue(maxPrice);

            // Leverage
            cell = row.createCell(6, CellType.NUMERIC);
            cell.setCellValue(leverage);

            // Profit, %
            int rownum = sheet.getPhysicalNumberOfRows();
            cell = row.createCell(7, CellType.FORMULA);
            String formula = "((F" + (rownum) + "/E" + (rownum) + ")-1)*100*G" + (rownum);
            cell.setCellFormula(formula);

            // Wallet
            cell = row.createCell(8, CellType.STRING);
            cell.setCellValue(wallet);

            XSSFFormulaEvaluator.evaluateAllFormulaCells(book);
            sheet.autoSizeColumn(1);
            FileOutputStream out = new FileOutputStream(file.getAbsolutePath());
            book.write(out);
            out.close();
            book.close();
        } catch (IOException e) {
            System.out.println("Tabulka neexistuje, vytvorte tubulku");
        }

    }

    /**
     * Metoda pro smazání řádku z tabulky
     *
     * @param index index řádku který chceme smazat
     * @throws IOException
     */
    public static void deleteRow(int index){
        try (FileInputStream is = new FileInputStream(file)) {
            Workbook book = new XSSFWorkbook(is);
            Sheet sheet = book.getSheetAt(0);
            Row remove = sheet.getRow(index - 1);
            sheet.removeRow(remove);
            sheet.shiftRows(index, sheet.getLastRowNum(), -1);
            Iterator<Row> rowIterator = sheet.iterator();
            int i = 0;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (i != 0) {
                    Cell cell;
                    int rownum = row.getRowNum() + 1;
                    cell = row.createCell(7, CellType.FORMULA);
                    String formula = "((F" + (rownum) + "/E" + (rownum) + ")-1)*100*G" + (rownum);
                    cell.setCellFormula(formula);
                }
                i++;
            }
            XSSFFormulaEvaluator.evaluateAllFormulaCells(book);
            sheet.autoSizeColumn(1);
            FileOutputStream out = new FileOutputStream(file.getAbsolutePath());
            book.write(out);
            is.close();
            book.close();
        } catch (IOException e) {
            System.out.println("Tabulka neexistuje, vytvorte tubulku");
        }
    }

    /**
     * Metoda pro hezky výpis tabulky na obrazovku
     *
     * @throws IOException
     */
    public static void printTable(){
        try (FileInputStream is = new FileInputStream(file)) {
            Workbook book = new XSSFWorkbook(is);
            Sheet sheet = book.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            int i = 1;
            int cellN = 0;
            while (rowIterator.hasNext()) {
                System.out.printf("%-4d", i);
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    cellN++;
                    Cell cell = cellIterator.next();
                    CellType cellType = cell.getCellType();
                    switch (cellType) {
                        case FORMULA:
                            FormulaEvaluator evaluator = book.getCreationHelper().createFormulaEvaluator();
                            System.out.printf("%12.2f", evaluator.evaluate(cell).getNumberValue());
                            break;
                        case NUMERIC:
                            if (cellN == 3) {
                                SimpleDateFormat df = new SimpleDateFormat("dd.MM.yy");
                                String d = df.format(cell.getDateCellValue());
                                System.out.print("\t  " + d);
                            } else {
                                System.out.printf("%12.2f", cell.getNumericCellValue());

                            }
                            break;
                        case STRING:
                            if (cellN == 1) {
                                System.out.printf("%6s", cell.getStringCellValue());
                            } else {
                                System.out.printf("%12s", cell.getStringCellValue());
                            }
                            break;
                    }
                }
                System.out.println();
                cellN = 0;
                i++;
            }
        }catch (IOException e){
            System.out.println("Tabulka neexistuje, vytvorte tubulku");
        }
    }
}