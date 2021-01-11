package Hurricane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static Hurricane.HurricaneData.readFileName;

public class Main {
        public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        HurricaneData hd = new HurricaneData();
        hd.read(readFileName());
        boolean end = false;
        while(!end){
            System.out.println("Vyberte jednu moznost: ");
            System.out.println("1. Vypsat informace o všech hurikánech v zadaném období");
            System.out.println("2. Vypsat kategorii a rychlost v km/h hurikánu zadaného jménem");
            System.out.println("3. Vypisat informace o hurikánech setříděných podle rychlosti.");
            System.out.println("0. Vychod");
            int vyber = sc.nextInt();
            switch (vyber){
                case 1:
                    System.out.println("Napiste rok, od ktereho zacit:");
                    int r1 = sc.nextInt();
                    System.out.println("Do ktereho roku vypsiovat?");
                    int r2 = sc.nextInt();
                    List<Hurricane> list = hd.findByYears(r1,r2);
                    for (Hurricane hu : list){
                        System.out.println(hu);
                    }
                    break;
                case 2:
                    System.out.println("Zadejte jmeno:");
                    String s = sc.next();
                    System.out.println(hd.findByName(s));
                    break;
                case 3:
                    hd.sortBySpeed();
                    hd.display();
                    break;
                case 0:
                    end =true;
                    break;
            }
            sc.close();
        }

    }
}
