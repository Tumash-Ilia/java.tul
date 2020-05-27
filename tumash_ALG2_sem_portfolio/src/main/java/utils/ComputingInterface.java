package utils;

import java.io.IOException;
import java.util.List;

public interface ComputingInterface {
    /**
     * Metoda pro vypocet poctu obchodu z tabulky
     *
     * @return int pocet vsech obchodu
     */
    public int allDeals() throws IOException;

    /**
     * Metoda pro vypocet pozitivnich obchodu z tabulky
     *
     * @return int int pocet pozitivnich obchodu
     */
    public int successfulDeals() throws IOException;

    /**
     * Metoda pro vypocet negativnich obchodu z tabulky
     *
     * @return int pocet negativnich obchodu
     */
    public int unsuccessfulDeals() throws IOException;

    /**
     * Metoda pro vypocet souctu uroku pozitivnich obchodu
     *
     * @return double soucet uroku pozitivnich obchodu
     */
    public double allProfit() throws IOException;

    /**
     * Metoda pro vypocet souctu uroku negativnich obchodu
     *
     * @return double soucet uroku negativnich obchodu
     */
    public double allLoss() throws IOException;

    /**
     * Metoda pro vypocet souctu uroku ze vsech obchodu
     *
     * @return double soucet uroku vsech obchodu
     */
    public double netProfit() throws IOException;

    /**
     * Metoda pro vypocet prumeru uroku vsech obchodu
     *
     * @return double prumerny urok vsech obchodu
     */
    public double averageProfit() throws IOException;


}