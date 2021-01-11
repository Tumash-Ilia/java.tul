package Hurricane;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class HurricaneData {
    static List<Hurricane> hurricane = new ArrayList<>();

    public void read(String fileName){
        File data = new File(fileName);
      try  (Scanner sc = new Scanner(data)) {
          while (sc.hasNext()) {
              int year = sc.nextInt();
              String month = sc.next();
              int pressure = sc.nextInt();
              int speed = sc.nextInt();
              String name = sc.next();
              Hurricane h = new Hurricane(year, month, pressure, speed, name);
              hurricane.add(h);
          }
      }catch (IOException e){
          System.out.println(fileName + " neexistuje");
          read(readFileName());
      }
    }

    public static String readFileName(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Zadejte nazev souboru: ");
        String s = sc.nextLine();
        s += ".txt";
        return s;
    }


    public List<Hurricane> findByYears(int year1, int year2) {
        List<Hurricane> list = new ArrayList<>();
        for (Hurricane h : hurricane) {
            if (h.getYear() >= year1 && h.getYear() <= year2) {
                list.add(h);
            }
        }
        return list;
    }

    public String findByName(String name) {
        for (Hurricane h : hurricane) {
            if (h.getName().equals(name)) {
                return "category: " + h.getHurricaneCategory() + " speed: " + h.getSpeedInKmh() + "km/h";
            }
        }
        return "Hurricane not found";
    }

    public void sortBySpeed() {
        Collections.sort(hurricane, Comparator.comparing(Hurricane::getSpeedInKmh));
    }


    public void display(){
        for (Hurricane hu : hurricane ) {
            System.out.println(hu);
        }
    }
}
