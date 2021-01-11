package UI;

import java.util.Scanner;

public class Menu {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean end = false;

        while(!end){
            System.out.println("1. Vypis predeta");
            System.out.println("2. Vypis znamek");
            System.out.println("3. Pridat znamku");
            System.out.println("4. ");
            System.out.println("5. ");
            System.out.println("6. ");
            System.out.println("7. ");
            System.out.println("0. Vychod");
            System.out.println("Zvolte funkce");
            int volba = sc.nextInt();

            switch (volba){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 0:
                    end = true;
                    break;
                default:
                    System.out.println("Chybna volba");

            }
        }

    }
}
