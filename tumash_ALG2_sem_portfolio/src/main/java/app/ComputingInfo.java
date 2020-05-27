package app;

import utils.ComputingInterface;

import java.io.IOException;
import java.util.List;

public class ComputingInfo implements ComputingInterface {

    @Override
    public int allDeals() throws IOException {
        List<Double> list = XlsxEditor.getProfitColumn();
        return list.size();
    }

    @Override
    public int successfulDeals() throws IOException {
        List<Double> list = XlsxEditor.getProfitColumn();
        int cnt = 0;
        for (int i = 0; i < list.size(); i++) {
            double a = list.get(i);
            if (a > 0) {
                cnt++;
            }
        }
        return cnt;
    }

    @Override
    public int unsuccessfulDeals() throws IOException {
        List<Double> list = XlsxEditor.getProfitColumn();
        return allDeals() - successfulDeals();
    }

    @Override
    public double allProfit() throws IOException {
        List<Double> list = XlsxEditor.getProfitColumn();
        double sum = 0;
        for (int i = 0; i < list.size(); i++) {
            double a = list.get(i);
            if (a > 0) {
                sum += a;
            }
        }
        return sum;
    }

    @Override
    public double allLoss() throws IOException {
        List<Double> list = XlsxEditor.getProfitColumn();
        double sum = 0;
        for (int i = 0; i < list.size(); i++) {
            double a = list.get(i);
            if (a < 0) {
                sum += a;
            }
        }
        return sum;
    }

    @Override
    public double netProfit() throws IOException {
        return allProfit() + allLoss();
    }

    @Override
    public double averageProfit() throws IOException {
        List<Double> list = XlsxEditor.getProfitColumn();
        return netProfit() / list.size();
    }

    /**
     * Metoda počítá procentní zisk na vklad při použití 5% vkladu
     *
     * @param name název účtu
     * @return procentní zisk na vklad při použití 5% vkladu
     * @throws IOException
     */
    public double depositProfit(String name) throws IOException {
        List<Double> list = XlsxEditor.geWalletProfit(name);
        double sum = 0;
        for (int i = 0; i < list.size(); i++) {
            double a = list.get(i);
            sum += a;
        }
        return sum / 20;
    }

//    public static void main(String[] args) throws IOException {
//        ComputingInfo cs = new ComputingInfo();
//        System.out.println(cs.depositProfit("BitMEX"));
//        System.out.println(cs.depositProfit("Binance"));
//        System.out.println(cs.netProfit());
//    }

}
