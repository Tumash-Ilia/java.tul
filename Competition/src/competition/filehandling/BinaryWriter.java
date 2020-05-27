package competition.filehandling;

import competition.app.Runner;
import java.io.*;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class BinaryWriter extends Writer {

    @Override
    public void saveResults(String resultFilepath, List<Runner> runners) throws IOException {
        File resultFile = new File(dataDirectory, resultFilepath);
        try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(resultFile))){
            dos.writeUTF("Nove vysledky");
            int n = 1;
            for (Runner runner : runners) {
                dos.writeInt(n);
                //dos.writeChar('.'); //zapisuju jenom data bez mezer, nových řádků a znaků důležitých pro výpis
                //dos.writeUTF(resultFilepath); //binarni soubory jsou primárne určené k uložení a ne prohlížení
                dos.writeUTF(runner.getFirstname());
                int nChars = runner.getLastname().length(); //simulace writeUTF, uložím počet znaků Stringu až pak String
                dos.writeInt(nChars);
                for (int i = 0; i < nChars; i++) {
                    dos.writeChar(runner.getLastname().charAt(i));
                }
                dos.writeLong(runner.runningTime().toNanoOfDay()); //čas vhodné uložit jako nanosekundy, ne String
                n++;
            }
        }
    }

    public void saveStart(String startFilePath) throws IOException {
        File binaryFile = new File(dataDirectory,startFilePath);
        File textFile = new File(dataDirectory,startFilePath.replace(".dat", ".txt"));
        try (Scanner out = new Scanner(textFile)) {
            try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(binaryFile))) {
                while (out.hasNext()) {
                    dos.writeInt(out.nextInt());
                    dos.writeUTF(out.next());
                    dos.writeUTF(out.next());
                    String startTime = out.next();
                    LocalTime start = LocalTime.parse(startTime, Runner.dtfstart);
                    dos.writeLong(start.toNanoOfDay());
                }
            }
        }

    }

    public void saveFinish(String finishFilePath) throws IOException {
        File binaryFile = new File(dataDirectory,finishFilePath);
        File textFile = new File(dataDirectory,finishFilePath.replace(".dat", ".txt"));
        try (Scanner out = new Scanner(textFile)) {
            try (DataOutputStream in = new DataOutputStream(new FileOutputStream(binaryFile))) {
                while (out.hasNext()) {
                    in.writeInt(out.nextInt());
                    String finishTime = out.next();
                    LocalTime finish = LocalTime.parse(finishTime, Runner.dtffinish);
                    in.writeLong(finish.toNanoOfDay());
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BinaryWriter bw = new BinaryWriter();
        String dir = "start.dat";
        bw.saveStart(dir);
        dir = "finish.dat";
        bw.saveFinish(dir);
    }
}

