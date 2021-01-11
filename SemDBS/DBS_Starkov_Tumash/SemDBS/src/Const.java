public class Const {

    /**
     * ADDRESS TABLE
     */
    public static final String ADDRESS_TABLE = "address";
    public static final String ADDRESS_ID = "id_address";
    public static final String ADDRESS_CITY = "city";
    public static final String ADDRESS_STREET = "street";
    public static final String ADDRESS_BUILDING = "building";
    public static final String ADDRESS_POSTCODE = "postcode";

    /**
     * MANUFACTURER TABLE
     */
    public static final String MANUFACTURER_TABLE = "manufacturer";
    public static final String MANUFACTURER_ID = "id_manufacturer";
    public static final String MANUFACTURER_TELEFON = "telefon";
    public static final String MANUFACTURER_ID_ADDRESS = "id_address";

    /**
     * MEDICINE TABLE
     */
    public static final String MEDICINE_TABLE = "medicine";
    public static final String MEDICINE_ID = "id_medicine";
    public static final String MEDICINE_NAME = "name";
    public static final String MEDICINE_EXP_DATE = "expiration_date";
    public static final String MEDICINE_PRICE = "price";
    public static final String MEDICINE_STRUCT = "structure";
    public static final String MEDICINE_COM_NAME = "company_name";

    /**
     * PHARMACY TABLE
     */
    public static final String PHARMACY_TABLE = "pharmacy";
    public static final String PHARMACY_ID = "id_pharmacy";
    public static final String PHARMACY_NAME = "name";
    public static final String PHARMACY_ID_ADDRESS = "id_address";

    /**
     * REQUEST TABLE
     */
    public static final String REQUEST_TABLE = "request";
    public static final String REQUEST_ID = "id_order";
    public static final String REQUEST_DATE = "date";
    public static final String REQUEST_COUNT = "count";
    public static final String REQUEST_ID_PHARMACY = "id_pharmacy";
    public static final String REQUEST_ID_STORAGE = "id_storage";
    public static final String REQUEST_ID_MEDICINE = "id_medicine";

    /**
     * STORAGE TABLE
     */
    public static final String STORAGE_TABLE = "storage";
    public static final String STORAGE_ID = "id_storage";
    public static final String STORAGE_ID_MEDICINE = "id_medicine";
    public static final String STORAGE_COUNT = "count";
    public static final String STORAGE_ID_ADDRESS = "id_address";


    /**
     * SUPPLY TABLE
     */
    public static final String SUPPLY_TABLE = "supply";
    public static final String SUPPLY_ID = "id_supply";
    public static final String SUPPLY_DATE = "date";
    public static final String SUPPLY_COUNT = "count";
    public static final String SUPPLY_ID_MANUFACTURER = "id_manufacturer";
    public static final String SUPPLY_ID_MEDICINE = "id_medicine";
    public static final String SUPPLY_ID_STORAGE = "id_storage";
}
