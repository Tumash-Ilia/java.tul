package APP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Znamky {


    int id;
    double znamka;
    double vaha;
    ArrayList<Znamky> znamkies = new ArrayList<>();

    public Znamky() {
    }

    public Znamky(int id, double znamka, double vaha) {
        this.id = id;
        this.znamka = znamka;
        this.vaha = vaha;
        znamkies.add(this);
    }

    public void loadZnamky(String path){
        File file = new File(path);
        try (FileInputStream fos = new FileInputStream(file)){
            Scanner sc = new Scanner(fos);

            while(sc.hasNext()){
                int number = sc.nextInt();
                double zn = sc.nextDouble();
                double v = sc.nextDouble();

              znamkies.add(new Znamky(number,zn,v));
            }

        }catch (IOException e){
            System.out.println("chyba");

        }

    }
    public void saveZn(Znamky znamka){
        File file = new File("dat\\znamky.txt");

        try (FileOutputStream fos = new FileOutputStream(file,true)) {
            StringBuilder sb = new StringBuilder();
            sb.append(znamka.id).append(" ").append(znamka.znamka).append(" ").append(znamka.vaha);
            sb.append("\n");
            String s = sb.toString();
            s = s.replace('.',',');
            fos.write(s.getBytes());
        }catch (IOException e){
            System.out.println("HUI");
        }


    }


    public void vypis(){
        for (Znamky znamk: znamkies) {
            System.out.println(znamk.id + " " + znamk.znamka + " " + znamk.vaha);
        }
    }

    public static void main(String[] args) {
        Znamky zn = new Znamky();
        zn.loadZnamky("dat\\znamky.txt");
        Znamky s =  new Znamky(1,2.4,2.5);
        zn.znamkies.add(s);
        zn.vypis();
        zn.saveZn(s);
    }

}
