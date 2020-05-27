package competition.app;

import competition.utils.IllegalFilenameException;
import competition.filehandling.BinaryWriter;
import competition.filehandling.TextWriter;
import competition.filehandling.Writer;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Competition {

    private ArrayList<Runner> runners = new ArrayList<>();

    public void load(String startFilepath, String finishFilepath) throws IOException {

        if (!startFilepath.contains("start")) {
            throw new IllegalFilenameException("Start soubor musi obsahovat start."); //vyhození vlastní výjimky
        }

        File startFile = new File(Writer.dataDirectory, startFilepath);
        try (Scanner inStart = new Scanner(startFile)) { //construktor vyhazuje výjimku (povinne ošetrovatelnou)
            while (inStart.hasNext()) {
                int number = inStart.nextInt();
                String firstname = inStart.next();
                String lastname = inStart.next();
                String startTime = inStart.next();
                Runner r = new Runner(number, firstname, lastname);
                r.setStartTime(startTime);
                runners.add(r);
            }
        }

        File finishFile = new File(Writer.dataDirectory, finishFilepath);
        BufferedReader inFinish = null;
        try {
            inFinish = new BufferedReader(new FileReader(finishFile));
            String line;
            while ((line = inFinish.readLine()) != null) { //102 10:02:00:000
                String[] parts = line.split("[ ]+");
                try { //ošetření výjimky odchycenim 
                    Runner r = findRunner(Integer.parseInt(parts[0]));
                    r.setFinishTime(parts[1]);
                } catch (NoSuchElementException e) {
                    System.err.print(e.getMessage()); //neexistujici bezec se preskoci
                }
            }
        } finally {
            if (inFinish != null) inFinish.close();
        }
    }

    private void loadBin(String startFilepath, String finishFilepath) throws IOException {
        BinaryWriter bw = new BinaryWriter();
        bw.saveStart(startFilepath);
        File startFile = new File(Writer.dataDirectory, startFilepath);
        try (DataInputStream dis = new DataInputStream((new FileInputStream(startFile)))) {
            boolean End = false;
            while (!End) {
                try {
                    int number = dis.readInt();
                    String firstName = dis.readUTF();
                    String lastName = dis.readUTF();
                    long startTimeNano = dis.readLong();
                    String startTime = (LocalTime.ofNanoOfDay(startTimeNano)).format(Runner.dtfstart);
                    Runner r = new Runner(number, firstName, lastName);
                    r.setStartTime(startTime);
                    runners.add(r);
                } catch (EOFException e) {
                    End = true;
                }
            }
        }

        bw.saveFinish(finishFilepath);
        File finishFile = new File(Writer.dataDirectory, finishFilepath);
        try (DataInputStream dis = new DataInputStream((new FileInputStream(finishFile)))) {
            boolean isEnd = false;
            while (!isEnd) {
                try {
                    int number = dis.readInt();
                    long finishTimeNano = dis.readLong();
                    String finishTime = (LocalTime.ofNanoOfDay(finishTimeNano)).format(Runner.dtffinish);
                    Runner r = findRunner(number);
                    r.setFinishTime(finishTime);
                } catch (EOFException e) {
                    isEnd = true;
                }
            }
        }
    }

    private Runner findRunner(int number) {
        for (Runner runner : runners) {
            if (runner.getNumber() == number) {
                return runner;
            }
        }
        throw new NoSuchElementException("Bezec s cislem " + number + " nebyl na startu."); //vyhozeni výjimky
    }

    public String getResults() {
        Collections.sort(runners);
        StringBuilder sb = new StringBuilder("");
        int n = 1;
        for (Runner runner : runners) {
            sb.append(String.format("%-2d. %s%n", n, runner));
            n++;
        }
        return sb.toString();
    }

    public void saveResults(String resultFilepath) throws IOException { //ošetření výjimky vyhozením výš 
        Collections.sort(runners);
        Writer w = null;
        if (resultFilepath.endsWith(".txt")) {
            w = new TextWriter();
        } else if (resultFilepath.endsWith(".dat")) {
            w = new BinaryWriter();
        } else {
            throw new IllegalArgumentException("Nepodporovana pripona souboru");
        }
        w.saveResults(resultFilepath, runners);
    }
}
