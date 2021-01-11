import org.w3c.dom.ls.LSOutput;

import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static DatabaseHandler databaseHandler = new DatabaseHandler();

    public static void main(String[] args) {
        mainMenu();
    }

    //menu
    private static void mainMenu() {
        boolean isEnd = false;
        int option;
        while (!isEnd) {
            System.out.println("---------------------------");
            System.out.println("1. Address");
            System.out.println("2. Manufacturer");
            System.out.println("3. Medicine");
            System.out.println("4. Pharmacy");
            System.out.println("5. Request");
            System.out.println("6. Storage");
            System.out.println("7. Supply");
            System.out.println("8. Složité SQL dotazy");
            System.out.println("9. Ukoncit program");
            System.out.println("---------------------------");
            option = loadOption();
            switch (option) {
                case 1:
                    menu("Address");
                    break;
                case 2:
                    menu("Manufacturer");
                    break;
                case 3:
                    menu("Medicine");
                    break;
                case 4:
                    menu("Pharmacy");
                    break;
                case 5:
                    menu("Request");
                    break;
                case 6:
                    menu("Storage");
                    break;
                case 7:
                    menu("Supply");
                    break;
                case 8:
                    menuSQL();
                    break;
                case 9:
                    isEnd = true;
                    break;
                default:
                    System.out.println("Neni spravny vstup!");
            }
        }
    }

    //vstup
    private static int loadOption() {
        String option = sc.nextLine();
        if (option.matches("[\\d]+")) {
            return Integer.parseInt(option);
        }
        return 0;
    }

    //SLOZITE DOTAZY MENU SQL
    private static void menuSQL()
    {
        boolean isEnd = false;
        int option;
        while (!isEnd) {
            System.out.println("1. vnořený SELECT v SELECT");
            System.out.println("2. vnořený SELECT ve FROM");
            System.out.println("3. vnořený SELECT ve WHERE");
            System.out.println("4. GROUP BY");
            System.out.println("5. množinova operace");
            System.out.println("6. LEFT JOIN");
            System.out.println("7. Zpět");
            option = loadOption();
            switch (option) {
                case 1:
                    databaseHandler.selectPriceMedicineselectAVGPrice();
                    break;
                case 2:
                    databaseHandler.selectFROMAddress();
                    break;
                case 3:
                    databaseHandler.selectAddressInStorage();
                    break;
                case 4:
                    databaseHandler.groupByManufacturer();
                    break;
                case 5:
                    databaseHandler.UnionIdAddress();
                    break;
                case 6:
                    databaseHandler.leftJoinMedicineSupply();
                    break;
                case 7:
                    isEnd = true;
                    break;
                default:
                    System.out.println("Zadejte validní vstup");
            }

        }
    }

    //address menu
    private static void menu(String table) {
        boolean isEnd = false;
        int option, index;
        while (!isEnd) {
            System.out.println("--------------------------Seznam " + table + "-------------------------");
            //načítání z databáze
            switch (table) {
                case "Address":
                    databaseHandler.printAddresTable();
                    break;
                case "Manufacturer":
                    databaseHandler.printManufacturerTable();
                    break;
                case "Medicine":
                    databaseHandler.printMedicineTable();
                    break;
                case "Pharmacy":
                    databaseHandler.printPharmacyTable();
                    break;
                case "Request":
                    databaseHandler.printRequestTable();
                    break;
                case "Storage":
                    databaseHandler.printStorageTable();
                    break;
                case "Supply":
                    databaseHandler.printSupplyTable();
                    break;
                default:
                    System.out.println("Zadejte validní vstup");
            }
            //načtení tabulky z databáze a její zobrazení
            System.out.println("---------------------------------------------------------------");
            System.out.println("1. Přidat");
            System.out.println("2. Změnit");
            System.out.println("3. Odebrat");
            System.out.println("4. Vyhledávání podle id");
            System.out.println("5. Zpět");
            System.out.println("---------------------------------------------------------------");
            option = loadOption();
            switch (option) {
                case 1:
                    //přidání
                    switch (table) {
                        case "Address":
                            try {
                                System.out.println("Zadejte město");
                                String mesto = sc.nextLine();
                                System.out.println("Zadejte název ulice");
                                String ulice = sc.nextLine();
                                System.out.println("Zadejte číslo domu");
                                String byt = sc.nextLine();
                                System.out.println("Zadejte PSČ");
                                String psc = sc.nextLine();
                                databaseHandler.addAddress(mesto, ulice, byt, psc);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case "Manufacturer":
                            try {
                                System.out.println("Zadejte telefon");
                                String telefon = sc.nextLine();
                                System.out.println("Zadejte id_adresa");
                                databaseHandler.printAddresTable();
                                String id_adresa = sc.nextLine();
                                databaseHandler.addManufacturer(telefon, id_adresa);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case "Medicine":
                            try {
                                System.out.println("Zadejte nazev");
                                String nazev = sc.nextLine();
                                System.out.println("Zadejte expire date");
                                String date = sc.nextLine();
                                System.out.println("Zadejte cenu");
                                String cena = sc.nextLine();
                                System.out.println("Zadejte zlozeni");
                                String zlozeni = sc.nextLine();
                                System.out.println("Zadejte vyrobce");
                                String vyrobce = sc.nextLine();
                                databaseHandler.addMedicine(nazev, date, cena, zlozeni, vyrobce);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case "Pharmacy":
                            try {
                                System.out.println("Zadejte nazev");
                                String nazev = sc.nextLine();
                                System.out.println("Zadejte id_adresu");
                                databaseHandler.printAddresTable();
                                String id_address = sc.nextLine();
                                databaseHandler.addPharmacy(nazev, id_address);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case "Request":
                            try {
                                System.out.println("Zadejte datum");
                                String date = sc.nextLine();
                                System.out.println("Zadejte mnozstvi");
                                String mnozstvi = sc.nextLine();
                                System.out.println("Zadejte id_pharmacy");
                                databaseHandler.printPharmacyTable();
                                String id_pharmacy = sc.nextLine();
                                System.out.println("Zadejte id_storage");
                                databaseHandler.printStorageTable();
                                String id_storage = sc.nextLine();
                                System.out.println("Zadejte id_medicine");
                                databaseHandler.printMedicineTable();
                                String id_medicine = sc.nextLine();
                                databaseHandler.addRequest(date, mnozstvi, id_pharmacy, id_storage,id_medicine);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case "Storage":
                            try {
                                System.out.println("Zadejte id_medicine");
                                databaseHandler.printMedicineTable();
                                String id_medicine = sc.nextLine();
                                System.out.println("Zadejte mnozstvi");
                                String mnozstvi = sc.nextLine();
                                System.out.println("Zadejte id_address");
                                databaseHandler.printAddresTable();
                                String id_address = sc.nextLine();
                                databaseHandler.addStorage(id_medicine, mnozstvi, id_address);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case "Supply":
                            try {
                                System.out.println("Zadejte datum");
                                String date = sc.nextLine();
                                System.out.println("Zadejte mnozstvi");
                                String mnozstvi = sc.nextLine();
                                System.out.println("Zadejte id_manufacturer");
                                databaseHandler.printManufacturerTable();
                                String id_manufacturer = sc.nextLine();
                                System.out.println("Zadejte id_medicine");
                                databaseHandler.printMedicineTable();
                                String id_medicine = sc.nextLine();
                                System.out.println("Zadejte id_storage");
                                databaseHandler.printStorageTable();
                                String id_storage = sc.nextLine();
                                databaseHandler.addSupply(date, mnozstvi, id_manufacturer, id_medicine, id_storage);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        default:
                            System.out.println("Zadejte validní vstup");
                    }
                    break;
                case 2:
                    switch (table) {
                        case "Address":
                            try {
                                System.out.println("Zadejte id adresy, kterou chcete zmenit");
                                String id = sc.nextLine();
                                System.out.println("Zadejte město");
                                String mesto = sc.nextLine();
                                System.out.println("Zadejte název ulice");
                                String ulice = sc.nextLine();
                                System.out.println("Zadejte číslo domu");
                                String byt = sc.nextLine();
                                System.out.println("Zadejte PSČ");
                                String psc = sc.nextLine();
                                databaseHandler.updateAddress(id, mesto, ulice, byt, psc);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case "Manufacturer":
                            try {
                                System.out.println("Zadejte id výrobce, kterou chcete zmenit");
                                String id = sc.nextLine();
                                System.out.println("Zadejte telefon");
                                String telefon = sc.nextLine();
                                System.out.println("Zadejte id_adresa");
                                databaseHandler.printAddresTable();
                                String id_adresa = sc.nextLine();
                                databaseHandler.updateManufacturer(id,telefon, id_adresa);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case "Medicine":
                            try {
                                System.out.println("Zadejte id léku, kterou chcete zmenit");
                                String id = sc.nextLine();
                                System.out.println("Zadejte nazev");
                                String nazev = sc.nextLine();
                                System.out.println("Zadejte expire date");
                                String date = sc.nextLine();
                                System.out.println("Zadejte cenu");
                                String cena = sc.nextLine();
                                System.out.println("Zadejte zlozeni");
                                String zlozeni = sc.nextLine();
                                System.out.println("Zadejte vyrobce");
                                String vyrobce = sc.nextLine();
                                databaseHandler.updateMedicine(id,nazev,date,cena,zlozeni,vyrobce);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case "Pharmacy":
                            try {
                                System.out.println("Zadejte id lékárny, kterou chcete zmenit");
                                String id = sc.nextLine();
                                System.out.println("Zadejte nazev");
                                String nazev = sc.nextLine();
                                System.out.println("Zadejte id_adresu");
                                databaseHandler.printAddresTable();
                                String id_address = sc.nextLine();
                                databaseHandler.updatePharmacy(id,nazev, id_address);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case "Request":
                            try {
                                System.out.println("Zadejte id žádosti, kterou chcete zmenit");
                                String id = sc.nextLine();
                                System.out.println("Zadejte datum");
                                String date = sc.nextLine();
                                System.out.println("Zadejte mnozstvi");
                                String mnozstvi = sc.nextLine();
                                System.out.println("Zadejte id_pharmacy");
                                databaseHandler.printPharmacyTable();
                                String id_pharmacy = sc.nextLine();
                                System.out.println("Zadejte id_storage");
                                databaseHandler.printStorageTable();
                                String id_storage = sc.nextLine();
                                System.out.println("Zadejte id_medicine");
                                databaseHandler.printMedicineTable();
                                String id_medicine = sc.nextLine();
                                databaseHandler.updateRequest(id, date, mnozstvi, id_pharmacy, id_storage,id_medicine);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case "Storage":
                            try {
                                System.out.println("Zadejte id skladu, kterou chcete zmenit");
                                String id = sc.nextLine();
                                System.out.println("Zadejte id medicine, kterou chcete zmenit");
                                databaseHandler.printMedicineTable();
                                String id_medicine = sc.nextLine();
                                System.out.println("Zadejte novy id medicine");
                                databaseHandler.printMedicineTable();
                                String id_medicine2 = sc.nextLine();
                                System.out.println("Zadejte mnozstvi");
                                String mnozstvi = sc.nextLine();
                                System.out.println("Zadejte id_address");
                                databaseHandler.printAddresTable();
                                String id_address = sc.nextLine();
                                databaseHandler.updateStorage(id,id_medicine,id_medicine2, mnozstvi, id_address);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case "Supply":
                            try {
                                System.out.println("Zadejte id dodávky, kterou chcete zmenit");
                                String id = sc.nextLine();
                                System.out.println("Zadejte datum");
                                String date = sc.nextLine();
                                System.out.println("Zadejte mnozstvi");
                                String mnozstvi = sc.nextLine();
                                System.out.println("Zadejte id_manufacturer");
                                databaseHandler.printManufacturerTable();
                                String id_manufacturer = sc.nextLine();
                                System.out.println("Zadejte id_medicine");
                                databaseHandler.printMedicineTable();
                                String id_medicine = sc.nextLine();
                                System.out.println("Zadejte id_storage");
                                databaseHandler.printStorageTable();
                                String id_storage = sc.nextLine();
                                databaseHandler.updateSupply(id,date, mnozstvi, id_manufacturer, id_medicine, id_storage);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        default:
                            System.out.println("Zadejte validní vstup");
                    }
                    //změna
                    break;
                case 3:
                    //vymazání
                    switch (table) {
                        case "Address":
                            try {
                                System.out.println("Zadejte id adresy, kterou chcete smazat");
                                String id = sc.nextLine();
                                databaseHandler.deleteAddress(id);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case "Manufacturer":
                            try {
                                System.out.println("Zadejte id výrobce, kterou chcete smazat");
                                String id = sc.nextLine();
                                databaseHandler.deleteManufacurer(id);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case "Medicine":
                            try {
                                System.out.println("Zadejte id léku, kterou chcete smazat");
                                String id = sc.nextLine();
                                databaseHandler.deleteMedicine(id);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case "Pharmacy":
                            try {
                                System.out.println("Zadejte id lékárny, kterou chcete smazat");
                                String id = sc.nextLine();
                                databaseHandler.deletePharmacy(id);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case "Request":
                            try {
                                System.out.println("Zadejte id žádosti, kterou chcete smazat");
                                String id = sc.nextLine();
                                databaseHandler.deleteRequest(id);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case "Storage":
                            try {
                                System.out.println("Zadejte id skladu, kterou chcete smazat");
                                String id = sc.nextLine();
                                databaseHandler.deleteStorage(id);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case "Supply":
                            try {
                                System.out.println("Zadejte id dodávky, kterou chcete smazat");
                                String id = sc.nextLine();
                                databaseHandler.deleteSupply(id);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        default:
                            System.out.println("Zadejte validní vstup");
                    }
                    break;
                case 4:
                    //Vyhledávání
                    switch (table) {
                        case "Address":
                            try {
                                System.out.println("Zadejte id adresy, podle které chcete najít");
                                String id = sc.nextLine();
                                databaseHandler.searchAddressById(id);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case "Manufacturer":
                            try {
                                System.out.println("Zadejte id výrobce, podle které chcete najít");
                                String id = sc.nextLine();
                                databaseHandler.searchManufacturerById(id);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case "Medicine":
                            try {
                                System.out.println("Zadejte id léku, podle které chcete najít");
                                String id = sc.nextLine();
                                databaseHandler.searchMedicineById(id);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case "Pharmacy":
                            try {
                                System.out.println("Zadejte id lékárny, podle které chcete najít");
                                String id = sc.nextLine();
                                databaseHandler.searchPharmacyById(id);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case "Request":
                            try {
                                System.out.println("Zadejte id žádosti, podle které chcete najít");
                                String id = sc.nextLine();
                                databaseHandler.searchRequestById(id);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case "Storage":
                            try {
                                System.out.println("Zadejte id skladu, podle které chcete najít");
                                String id = sc.nextLine();
                                databaseHandler.searchStorageById(id);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case "Supply":
                            try {
                                System.out.println("Zadejte id dodávky, podle které chcete najít");
                                String id = sc.nextLine();
                                databaseHandler.searchSupplyById(id);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        default:
                            System.out.println("Zadejte validní vstup");
                    }
                    break;
                case 5:
                    isEnd = true;
                    break;
                default:
                    System.out.println("Zadejte validní vstup");
            }
        }
    }

}
