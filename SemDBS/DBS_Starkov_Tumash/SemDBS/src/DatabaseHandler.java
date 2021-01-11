import java.sql.*;
import java.util.Scanner;

public class DatabaseHandler extends Configs {
    Connection dbConnection;
    public Scanner sc = new Scanner(System.in);

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName +
                "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        //   Class.forName("com.mysql.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPassword);
        return dbConnection;
    }

    //přidání do databáze
    public void addStorage(String id_medicine, String count, String id_address) {
        String insert = "INSERT INTO " + Const.STORAGE_TABLE + "(" + Const.STORAGE_ID_MEDICINE + "," +
                Const.STORAGE_COUNT + "," + Const.STORAGE_ID_ADDRESS + ")" +
                "VALUES(?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, id_medicine);
            prSt.setString(2, count);
            prSt.setString(3, id_address);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Jeden nebo vice parametru jsou nespravne, zkuste to znovu");
        }
    }

    public void addStorage(String id_storage, String id_medicine, String count, String id_address) {
        String insert = "INSERT INTO " + Const.STORAGE_TABLE + "(" + Const.STORAGE_ID + "," + Const.STORAGE_ID_MEDICINE + "," +
                Const.STORAGE_COUNT + "," + Const.STORAGE_ID_ADDRESS + ")" +
                "VALUES(?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, id_storage);
            prSt.setString(2, id_medicine);
            prSt.setString(3, count);
            prSt.setString(4, id_address);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Jeden nebo vice parametru jsou nespravne, zkuste to znovu");
        }
    }

    public void addManufacturer(String telefon, String id_address) {
        String insert = "INSERT INTO " + Const.MANUFACTURER_TABLE + "(" + Const.MANUFACTURER_TELEFON + "," +
                Const.MANUFACTURER_ID_ADDRESS + ")" +
                "VALUES(?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, telefon);
            prSt.setString(2, id_address);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Jeden nebo vice parametru jsou nespravne, zkuste to znovu");
        }
    }

    public void addAddress(String city, String street, String building, String postcode) {
        String insert = "INSERT INTO " + Const.ADDRESS_TABLE + "(" + Const.ADDRESS_CITY + "," +
                Const.ADDRESS_STREET + "," + Const.ADDRESS_BUILDING + "," +
                Const.ADDRESS_POSTCODE + ")" +
                "VALUES(?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, city);
            prSt.setString(2, street);
            prSt.setString(3, building);
            prSt.setString(4, postcode);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Jeden nebo vice parametru jsou nespravne, zkuste to znovu");
        }
    }

    public void addPharmacy(String name, String id_address) {
        String insert = "INSERT INTO " + Const.PHARMACY_TABLE + "(" + Const.PHARMACY_NAME + "," +
                Const.PHARMACY_ID_ADDRESS + ")" +
                "VALUES(?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, name);
            prSt.setString(2, id_address);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Jeden nebo vice parametru jsou nespravne, zkuste to znovu");
        }
    }

    // date = YYYY/MM/DD
    public void addMedicine(String name, String exp_date, String price, String structure, String companyName) {
        String insert = "INSERT INTO " + Const.MEDICINE_TABLE + "(" + Const.MEDICINE_NAME + "," +
                Const.MEDICINE_EXP_DATE + "," + Const.MEDICINE_PRICE + "," +
                Const.MEDICINE_STRUCT + "," + Const.MEDICINE_COM_NAME + ")" +
                "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, name);
            prSt.setString(2, exp_date);
            prSt.setString(3, price);
            prSt.setString(4, structure);
            prSt.setString(5, companyName);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Jeden nebo vice parametru jsou nespravne, zkuste to znovu");
        }
    }

    // date = YYYY/MM/DD
    public void addSupply(String date, String count, String id_manufacturer, String id_medicine, String id_storage) {
        ResultSet rs = getStorageInfo(id_storage, id_medicine);
        ResultSet searchResult = searchStorageById(id_storage);
        try {
            if (rs.next()) {
                Integer cnt = rs.getInt(3);
                Integer id_address = rs.getInt(4);
                Integer cnt2 = Integer.parseInt(count);
                Integer newCnt = cnt + cnt2;
                updateStorage(id_storage, id_medicine, id_medicine, newCnt.toString(), id_address.toString());
            } else if (searchResult.next()) {
                Integer id_address = searchResult.getInt(4);
                addStorage(id_storage, id_medicine, count, id_address.toString());
            } else {
                System.out.println("Zadany sklad neexistuje, nejprve vytvorte sklad");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String insert = "INSERT INTO " + Const.SUPPLY_TABLE + "(" + Const.SUPPLY_DATE + "," +
                Const.SUPPLY_COUNT + "," + Const.SUPPLY_ID_MANUFACTURER + "," +
                Const.SUPPLY_ID_MEDICINE + "," + Const.SUPPLY_ID_STORAGE + ")" +
                "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, date);
            prSt.setString(2, count);
            prSt.setString(3, id_manufacturer);
            prSt.setString(4, id_medicine);
            prSt.setString(5, id_storage);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Jeden nebo vice parametru jsou nespravne, zkuste to znovu");
        }

    }

    // date = YYYY/MM/DD
    public void addRequest(String date, String count, String id_pharmacy, String id_storage, String id_medicine) {
        ResultSet rs = getStorageInfo(id_storage, id_medicine);
        try {
            if (rs.next()) {
                Integer cnt = rs.getInt(3);
                Integer id_address = rs.getInt(4);
                Integer cnt2 = Integer.parseInt(count);
                if (cnt < cnt2) {
                    System.out.println("Lek nelze objednat, pocet leku na sklade je nedostatecny");
                    return;
                } else {
                    Integer newCnt = cnt - cnt2;
                    updateStorage(id_storage, id_medicine, id_medicine, newCnt.toString(), id_address.toString());
                }
            } else {
                System.out.println("Zadany sklad neexistuje, nebo lek neni skladem");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String insert = "INSERT INTO " + Const.REQUEST_TABLE + "(" + Const.REQUEST_DATE + "," +
                Const.REQUEST_COUNT + "," + Const.REQUEST_ID_PHARMACY + "," +
                Const.REQUEST_ID_STORAGE + "," + Const.REQUEST_ID_MEDICINE + ")" +
                "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, date);
            prSt.setString(2, count);
            prSt.setString(3, id_pharmacy);
            prSt.setString(4, id_storage);
            prSt.setString(5, id_medicine);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Jeden nebo vice parametru jsou nespravne, zkuste to znovu");
        }
    }

    public ResultSet getStorageInfo(String id_storage, String id_medicine) {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.STORAGE_TABLE + " WHERE " +
                Const.STORAGE_ID + "=? AND " + Const.SUPPLY_ID_MEDICINE + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, id_storage);
            prSt.setString(2, id_medicine);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Chyba pri vyhledani id id");
        }
        return resSet;
    }

    //výpis z databáze
    public void printAddresTable() {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.ADDRESS_TABLE;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.printf("%-8s%-20s%-20s%-10s%-10s%n", "ID", "City", "Street", "Building", "Postcode");
        while (true) {
            try {
                if (!resSet.next()) break;
                int id_addr = resSet.getInt(1);
                String city = resSet.getString(2);
                String street = resSet.getString(3);
                int building = resSet.getInt(4);
                int postcode = resSet.getInt(5);
                System.out.printf("%-8d%-20s%-20s%-10d%-10d\n", id_addr, city, street, building, postcode);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void printManufacturerTable() {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.MANUFACTURER_TABLE;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.printf("%-20s%-20s%-8s\n", "ID_MANUFACTURER", "TELEFON", "ID_ADDRESS");
        while (true) {
            try {
                if (!resSet.next()) break;
                int id_manufacturer = resSet.getInt(1);
                String telefon = resSet.getString(2);
                int id_address = resSet.getInt(3);
                System.out.printf("%-20d%-20s%-8d\n", id_manufacturer, telefon, id_address);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void printMedicineTable() {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.MEDICINE_TABLE;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.printf("%-14s%-20s%-30s%-20s%-30s%-20s\n", "ID_MEDICINE", "JMENO", "SKLADOVATELNOST", "CENA", "SLOZENI", "VYROBCE");
        while (true) {
            try {
                if (!resSet.next()) break;
                int id_medicine = resSet.getInt(1);
                String name = resSet.getString(2);
                String expiration_date = resSet.getString(3);
                String price = resSet.getString(4);
                String structure = resSet.getString(5);
                String company_name = resSet.getString(6);
                System.out.printf("%-14d%-20s%-30s%-20s%-30s%-20s\n", id_medicine, name, expiration_date, price, structure, company_name);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void printPharmacyTable() {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.PHARMACY_TABLE;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.printf("%-14s%-20s%-14s\n", "ID_PHARMACY", "JMENO", "ID_ADDRESS");
        while (true) {
            try {
                if (!resSet.next()) break;
                int id_pharmacy = resSet.getInt(1);
                String name = resSet.getString(2);
                int id_address = resSet.getInt(3);
                System.out.printf("%-14d%-20s%-14d\n", id_pharmacy, name, id_address);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void printRequestTable() {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.REQUEST_TABLE;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.printf("%-14s%-20s%-14s%-14s%-14s%-14s\n", "ID_ORDER", "DATE", "COUNT", "ID_PHARMACY", "ID_STORAGE", "ID_MEDICINE");
        while (true) {
            try {
                if (!resSet.next()) break;
                int id_order = resSet.getInt(1);
                String date = resSet.getString(2);
                int count = resSet.getInt(3);
                int id_pharmacy = resSet.getInt(4);
                int id_storage = resSet.getInt(5);
                int id_medicine = resSet.getInt(6);
                System.out.printf("%-14d%-20s%-14d%-14d%-14d%-14d\n", id_order, date, count, id_pharmacy, id_storage, id_medicine);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void printStorageTable() {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.STORAGE_TABLE;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.printf("%-14s%-14s%-14s%-14s\n", "ID_STORAGE", "ID_MEDICINE", "COUNT", "ID_ADDRESS");
        while (true) {
            try {
                if (!resSet.next()) break;
                int id_storage = resSet.getInt(1);
                int id_medicine = resSet.getInt(2);
                int count = resSet.getInt(3);
                int id_address = resSet.getInt(4);
                System.out.printf("%-14d%-14d%-14d%-14d\n", id_storage, id_medicine, count, id_address);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void printSupplyTable() {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.SUPPLY_TABLE;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.printf("%-14s%-14s%-14s%-14s%-14s%-14s\n", "ID_SUPPLY", "DATE", "COUNT", "ID_MANUFACTURER", "ID_MEDICINE", "ID_STORAGE");
        while (true) {
            try {
                if (!resSet.next()) break;
                int id_supply = resSet.getInt(1);
                String date = resSet.getString(2);
                int count = resSet.getInt(3);
                int id_manufacturer = resSet.getInt(4);
                int id_medicine = resSet.getInt(5);
                int id_storage = resSet.getInt(6);
                System.out.printf("%-14d%-14s%-14d%-14d%-14d%-14d\n", id_supply, date, count, id_manufacturer, id_medicine, id_storage);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //mazání z databáze
    public void deleteAddress(String id_address) {
        String insert = "DELETE FROM " + Const.ADDRESS_TABLE + " WHERE " + Const.ADDRESS_ID + " = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, id_address);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Adresu s zadanym id nejde smazat");
        }
    }

    public void deleteManufacurer(String id_manufacturer) {
        String insert = "DELETE FROM " + Const.MANUFACTURER_TABLE + " WHERE " + Const.MANUFACTURER_ID + " = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, id_manufacturer);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Vyrobce s zadanym id nejde smazat");
        }
    }

    public void deleteMedicine(String id_address) {
        String insert = "DELETE FROM " + Const.ADDRESS_TABLE + " WHERE " + Const.ADDRESS_ID + " = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, id_address);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lek s zadanym id nejde smazat");
        }
    }

    public void deletePharmacy(String id_pharmacy) {
        String insert = "DELETE FROM " + Const.PHARMACY_TABLE + " WHERE " + Const.PHARMACY_ID + " = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, id_pharmacy);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lekarnu s zadanym id nejde smazat");
        }
    }

    public void deleteRequest(String id_request) {
        String insert = "DELETE FROM " + Const.REQUEST_TABLE + " WHERE " + Const.REQUEST_ID + " = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, id_request);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Obejednavku s zadanym id nejde smazat");
        }
    }

    public void deleteStorage(String id_storage) {
        String insert = "DELETE FROM " + Const.STORAGE_TABLE + " WHERE " + Const.STORAGE_ID + " = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, id_storage);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Sklad s zadanym id nejde smazat");
        }
    }

    public void deleteSupply(String id_supply) {
        String insert = "DELETE FROM " + Const.SUPPLY_TABLE + " WHERE " + Const.SUPPLY_ID + " = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, id_supply);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Dodavku s zadanym id nejde smazat");
        }
    }

    //změna v databázi
    // UPDATE `semdbs`.`address` SET `city` = 'ss', `street` = 'sss', `building` = '4' WHERE (`id_address` = '30') and (`id_medicine` = '9');

    public void updateStorage(String id_storage, String id_medicine, String newid_medicine, String count, String id_address) {
        String insert = "UPDATE " + Const.STORAGE_TABLE + " SET " + Const.STORAGE_ID_MEDICINE + " = ?, " +
                Const.STORAGE_COUNT + " = ?, " + Const.STORAGE_ID_ADDRESS + " = ?" +
                " WHERE (" + Const.STORAGE_ID + " = ?) AND (" + Const.STORAGE_ID_MEDICINE + " =?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, newid_medicine);
            prSt.setString(2, count);
            prSt.setString(3, id_address);
            prSt.setString(4, id_storage);
            prSt.setString(5, id_medicine);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Chyba pri aktulizaci, zadana nespravna data");
        }
    }

    public void updateManufacturer(String id_manufacturer, String telefon, String id_address) {
        String insert = "UPDATE " + Const.MANUFACTURER_TABLE + " SET " + Const.MANUFACTURER_TELEFON + " = ?, " +
                Const.MANUFACTURER_ID_ADDRESS + " = ?" + " WHERE (" + Const.MANUFACTURER_ID + " =?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, telefon);
            prSt.setString(2, id_address);
            prSt.setString(3, id_manufacturer);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Chyba pri aktulizaci, zadana nespravna data");
        }
    }

    public void updateAddress(String id_address, String city, String street, String building, String postcode) {
        String insert = "UPDATE " + Const.ADDRESS_TABLE + " SET " + Const.ADDRESS_CITY + " =?, " +
                Const.ADDRESS_STREET + " =?, " + Const.ADDRESS_BUILDING + " =?, " +
                Const.ADDRESS_POSTCODE + " =?" + " WHERE (" + Const.ADDRESS_ID + " =?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, city);
            prSt.setString(2, street);
            prSt.setString(3, building);
            prSt.setString(4, postcode);
            prSt.setString(5, id_address);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Chyba pri aktulizaci, zadana nespravna data");
        }
    }

    public void updatePharmacy(String id_pharmacy, String name, String id_address) {
        String insert = "UPDATE " + Const.PHARMACY_TABLE + " SET " + Const.PHARMACY_NAME + " =?, " +
                Const.PHARMACY_ID_ADDRESS + " =?" + " WHERE(" + Const.PHARMACY_ID + " =?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, name);
            prSt.setString(2, id_address);
            prSt.setString(3, id_pharmacy);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Chyba pri aktulizaci, zadana nespravna data");
        }
    }

    // date = YYYY/MM/DD
    public void updateMedicine(String id_medicine, String name, String exp_date, String price, String structure, String companyName) {
        String insert = "UPDATE " + Const.MEDICINE_TABLE + " SET " + Const.MEDICINE_NAME + " =?, " +
                Const.MEDICINE_EXP_DATE + " =?, " + Const.MEDICINE_PRICE + " =?, " +
                Const.MEDICINE_STRUCT + " =?, " + Const.MEDICINE_COM_NAME + " =?" + " WHERE(" +
                Const.MEDICINE_ID + " =?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, name);
            prSt.setString(2, exp_date);
            prSt.setString(3, price);
            prSt.setString(4, structure);
            prSt.setString(5, companyName);
            prSt.setString(6, id_medicine);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Chyba pri aktulizaci, zadana nespravna data");
        }
    }

    // date = YYYY/MM/DD
    public void updateSupply(String id_supply, String date, String count, String id_manufacturer, String id_medicine, String id_storage) {
        String insert = "UPDATE " + Const.SUPPLY_TABLE + " SET " + Const.SUPPLY_DATE + " =?, " +
                Const.SUPPLY_COUNT + " =?, " + Const.SUPPLY_ID_MANUFACTURER + " =?, " +
                Const.SUPPLY_ID_MEDICINE + " =?, " + Const.SUPPLY_ID_STORAGE + " =?" +
                " WHERE(" + Const.SUPPLY_ID + " =?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, date);
            prSt.setString(2, count);
            prSt.setString(3, id_manufacturer);
            prSt.setString(4, id_medicine);
            prSt.setString(5, id_storage);
            prSt.setString(6, id_supply);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Chyba pri aktulizaci, zadana nespravna data");
        }
    }

    // date = YYYY/MM/DD
    public void updateRequest(String id_request, String date, String count, String id_pharmacy, String id_storage, String id_medicine) {
        String insert = "UPDATE " + Const.REQUEST_TABLE + " SET " + Const.REQUEST_DATE + " =?, " +
                Const.REQUEST_COUNT + " =?, " + Const.REQUEST_ID_PHARMACY + " =?, " +
                Const.REQUEST_ID_STORAGE + " =?, " + Const.REQUEST_ID_MEDICINE + " =?" +
                " WHERE(" + Const.REQUEST_ID + " =?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, date);
            prSt.setString(2, count);
            prSt.setString(3, id_pharmacy);
            prSt.setString(4, id_storage);
            prSt.setString(5, id_medicine);
            prSt.setString(6, id_request);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Chyba pri aktulizaci, zadana nespravna data");
        }
    }

    // Ziskani dat
    public ResultSet searchStorageById(String id_storage) {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.STORAGE_TABLE + " WHERE " +
                Const.STORAGE_ID + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, id_storage);
            resSet = prSt.executeQuery();
            while (resSet.next()) {
                int id_storage2 = resSet.getInt(1);
                int id_medicine = resSet.getInt(2);
                int count = resSet.getInt(3);
                int id_address = resSet.getInt(4);
                System.out.printf("%-14d%-14d%-14d%-14d\n", id_storage2, id_medicine, count, id_address);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Chyba pri ziskani podle id");
        }
        return resSet;
    }

    public ResultSet searchAddressById(String id_address) {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.ADDRESS_TABLE + " WHERE " +
                Const.ADDRESS_ID + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, id_address);
            resSet = prSt.executeQuery();
            while (resSet.next()) {
                int id_addr = resSet.getInt(1);
                String city = resSet.getString(2);
                String street = resSet.getString(3);
                int building = resSet.getInt(4);
                int postcode = resSet.getInt(5);
                System.out.printf("%-8d%-20s%-20s%-10d%-10d\n", id_addr, city, street, building, postcode);
            }
        }
        catch (SQLException | ClassNotFoundException e) {
            System.out.println("Chyba pri ziskani podle id");
        }
        return resSet;
    }

    public ResultSet searchManufacturerById(String id_manufacturer){
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.MANUFACTURER_TABLE + " WHERE " +
                Const.MANUFACTURER_ID + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1,id_manufacturer);
            resSet = prSt.executeQuery();
            while (resSet.next()) {
                int id_manufacture = resSet.getInt(1);
                String telefon = resSet.getString(2);
                int id_address = resSet.getInt(3);
                System.out.printf("%-20d%-20s%-8d\n", id_manufacture, telefon, id_address);

            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Chyba pri ziskani podle id");
        }
        return resSet;
    }
    public ResultSet searchMedicineById(String id_medicine){
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.MEDICINE_TABLE + " WHERE " +
                Const.MEDICINE_ID + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1,id_medicine);
            resSet = prSt.executeQuery();
            while (resSet.next()) {
                int id_medicin = resSet.getInt(1);
                String name = resSet.getString(2);
                String expiration_date = resSet.getString(3);
                String price = resSet.getString(4);
                String structure = resSet.getString(5);
                String company_name = resSet.getString(6);
                System.out.printf("%-14d%-20s%-30s%-20s%-30s%-20s\n", id_medicin, name, expiration_date, price, structure, company_name);

            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Chyba pri ziskani podle id");
        }
        return resSet;
    }
    public ResultSet searchPharmacyById(String id_pharmacy){
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.PHARMACY_TABLE + " WHERE " +
                Const.PHARMACY_ID + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1,id_pharmacy);
            resSet = prSt.executeQuery();
            while (resSet.next()) {
                int id_pharmac = resSet.getInt(1);
                String name = resSet.getString(2);
                int id_address = resSet.getInt(3);
                System.out.printf("%-14d%-20s%-14d\n", id_pharmac, name, id_address);

            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Chyba pri ziskani podle id");
        }
        return resSet;
    }
    public ResultSet searchRequestById(String id_request){
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.ADDRESS_TABLE + " WHERE " +
                Const.ADDRESS_ID + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1,id_request);
            resSet = prSt.executeQuery();
            while (resSet.next()) {
                int id_order = resSet.getInt(1);
                String date = resSet.getString(2);
                int count = resSet.getInt(3);
                int id_pharmacy = resSet.getInt(4);
                int id_storage = resSet.getInt(5);
                int id_medicine = resSet.getInt(6);
                System.out.printf("%-14d%-20s%-14d%-14d%-14d%-14d\n", id_order, date, count, id_pharmacy, id_storage, id_medicine);

            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Chyba pri ziskani podle id");
        }
        return resSet;
    }
    public ResultSet searchSupplyById(String id_supply){
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.ADDRESS_TABLE + " WHERE " +
                Const.ADDRESS_ID + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1,id_supply);
            resSet = prSt.executeQuery();
            while (resSet.next()) {
                int id_supply2 = resSet.getInt(1);
                String date = resSet.getString(2);
                int count = resSet.getInt(3);
                int id_manufacturer = resSet.getInt(4);
                int id_medicine = resSet.getInt(5);
                int id_storage = resSet.getInt(6);
                System.out.printf("%-14d%-14s%-14d%-14d%-14d%-14d\n", id_supply2, date, count, id_manufacturer, id_medicine, id_storage);

            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Chyba pri ziskani podle id");
        }
        return resSet;
    }

    //vnořený SELECT v SELECT
    //SELECT name,price FROM Medicine
    //WHERE price > (SELECT AVG(price) FROM Medicine)
    public ResultSet selectPriceMedicineselectAVGPrice() {
        ResultSet resSet = null;
        String select = "SELECT " + Const.MEDICINE_NAME + " , " + Const.MEDICINE_PRICE + " FROM " + Const.MEDICINE_TABLE + " WHERE " +
                Const.MEDICINE_PRICE + " > (SELECT AVG(" + Const.MEDICINE_PRICE  +
                ") FROM " + Const.MEDICINE_TABLE + ")";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
            System.out.printf("%-20s%-20s\n", "Nazev", "Cena");
            while (resSet.next()) {
                String name = resSet.getString(1);
                String price = resSet.getString(2);
                System.out.printf("%-20s%-20s\n", name,price);
            }
        }
        catch (SQLException | ClassNotFoundException e) {
            System.out.println("Chyba SELECT FROM SELECT");
        }
        return resSet;
    }

    //SELECT * FROM (
    //SELECT Pharmacy.name FROM Pharmacy where name = 'EUC'
    //) as SQLDOTAZ
    // vnořený SELECT ve FROM
    public ResultSet selectFROMAddress() {
        ResultSet resSet = null;
        String select = "SELECT * FROM (SELECT Pharmacy.name FROM Pharmacy where name = 'EUC') as SQLDOTAZ";
//        String select = "SELECT * FROM ( SELECT " + Const.PHARMACY_NAME + " FROM " +
//                Const.PHARMACY_TABLE + " Where " + Const.PHARMACY_NAME  +
//                " = Dr.Max) as SQLDOTAZ";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
            System.out.printf("%-20s\n", "Nazev");
            while (resSet.next()) {
                String name = resSet.getString(1);
                System.out.printf("%-20s\n", name);
            }
        }
        catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
            System.out.println("Chyba pri vyhledani Pharmacy");
        }
        return resSet;
    }

    // SELECT * FROM address WHERE id_address IN (SELECT id_address FROM storage);
    // vnořený SELECT ve WHERE
    public ResultSet selectAddressInStorage() {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.ADDRESS_TABLE + " WHERE " +
                Const.ADDRESS_ID + " IN (SELECT " + Const.STORAGE_ID_ADDRESS  +
                " FROM " + Const.STORAGE_TABLE + ")";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
            System.out.printf("%-8s%-20s%-20s%-10s%-10s%n", "ID", "City", "Street", "Building", "Postcode");
            while (resSet.next()) {
                int id_addr = resSet.getInt(1);
                String city = resSet.getString(2);
                String street = resSet.getString(3);
                int building = resSet.getInt(4);
                int postcode = resSet.getInt(5);
                System.out.printf("%-8d%-20s%-20s%-10d%-10d\n", id_addr, city, street, building, postcode);
            }
        }
        catch (SQLException | ClassNotFoundException e) {
            System.out.println("Chyba pri vyhledani adresy");
        }
        return resSet;
    }

    // Group by
    // SELECT id_manufacturer, COUNT(id_manufacturer) AS Qty_manufacturer,
    //    AVG(count) AS Avg_count
    //FROM supply
    //GROUP BY id_manufacturer;

    public ResultSet groupByManufacturer() {
        ResultSet resSet = null;
        String select = "SELECT " + Const.SUPPLY_ID_MANUFACTURER + ", COUNT(" +
                Const.SUPPLY_ID_MANUFACTURER + ") AS Supply_Count, AVG(" + Const.SUPPLY_COUNT  +
                ") AS AVG_count FROM " + Const.SUPPLY_TABLE + " GROUP BY " + Const.SUPPLY_ID_MANUFACTURER;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
            System.out.printf("%-8s%-13s%-8s%n", "ID", "Supply_Count", "AVG_count");
            while (resSet.next()) {
                int id_manufacturer = resSet.getInt(1);
                int supplyCnt = resSet.getInt(2);
                double avgCnt = resSet.getDouble(3);
                System.out.printf("%-8d%-13d%-8f%n", id_manufacturer, supplyCnt, avgCnt);
            }
        }
        catch (SQLException | ClassNotFoundException e) {
            System.out.println("Chyba pri group by manufacturer");
        }
        return resSet;
    }


    //SELECT city, street, building, postcode from Address, Manufacturer where Address.id_address = Manufacturer.id_address
    //       UNION
    //SELECT city, street, building, postcode from Pharmacy, Address where Address.id_address = Pharmacy.id_address;
    //množinova operace
    public ResultSet UnionIdAddress() {
        ResultSet resSet = null;
        String select = "SELECT city, street, building, postcode from Address, Manufacturer where Address.id_address = Manufacturer.id_address UNION SELECT city, street, building, postcode from Pharmacy, Address where Address.id_address = Pharmacy.id_address;";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
            System.out.printf("%-20s%-20s%-10s%-10s\n", "MESTO", "ULICE", "DOM", "PSC");
            while (resSet.next()) {
                String city = resSet.getString(1);
                String street = resSet.getString(2);
                int building = resSet.getInt(3);
                int postcode = resSet.getInt(4);
                System.out.printf("%-20s%-20s%-10d%-10d\n", city, street, building, postcode);
            }
        }
        catch (SQLException | ClassNotFoundException e) {
            System.out.println("Chyba union ID");
            System.out.println(e);
        }
        return resSet;
    }

    //LEFT JOIN
    //SELECT *
    //FROM Supply LEFT JOIN Medicine
    //ON Supply.id_medicine = medicine.id_medicine
    public ResultSet leftJoinMedicineSupply() {
        ResultSet resSet = null;
        String select = "SELECT * FROM Supply LEFT JOIN Medicine ON Supply.id_medicine = medicine.id_medicine";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
            System.out.printf("%-14s%-20s%-10s%-16s%-14s%-14s%-20s%-30s%-20s%-30s%-20s\n", "ID_SUPPLY", "DATE", "COUNT", "ID_MANUFACTURER", "ID_STORAGE", "ID_MEDICINE","NAME", "DATE", "PRICE","STRUCTURE","COMPANY NAME");
            while (resSet.next()) {
                int id_supply = resSet.getInt(1);
                String date = resSet.getString(2);
                int count = resSet.getInt(3);
                int id_manufacturer = resSet.getInt(4);
                int id_storage = resSet.getInt(5);
                int id_medicine = resSet.getInt(6);
                String name = resSet.getString(8);
                String expiration_date = resSet.getString(9);
                String price = resSet.getString(10);
                String structure = resSet.getString(11);
                String company_name = resSet.getString(12);
                System.out.printf("%-14d%-20s%-10d%-16d%-14d%-14d%-20s%-30s%-20s%-30s%-20s\n", id_supply, date, count,id_manufacturer,id_storage, id_medicine,name, expiration_date, price, structure, company_name);
            }
        }
        catch (SQLException | ClassNotFoundException e) {
            System.out.println("Chyba LEFT JOIN");
            System.out.println(e);
        }
        return resSet;
    }
}
