package competition.utils;

import competition.app.Runner;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalTime;

public class ReadResult {
    
    public static void main(String[] args) {
        try {
            readResult("result.dat");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void readResult(String resultFilepath) throws FileNotFoundException, IOException{
        try(DataInputStream dis = new DataInputStream(new FileInputStream(resultFilepath))){
            boolean isEnd = false;
            System.out.println(dis.readUTF());
            while(!isEnd){
                try{                    
                    System.out.print(dis.readInt() + ". ");
                    String firstname = dis.readUTF();
                    int nChars = dis.readInt();
                    String lastname = "";
                    for (int i = 0; i < nChars; i++) {
                        lastname += dis.readChar();
                    }
                    LocalTime runningTime = LocalTime.ofNanoOfDay(dis.readLong());
                    System.out.println(firstname + " " + lastname + " " + runningTime.format(Runner.dtffinish));                   
                }catch(EOFException e){ //zjištění konce souboru
                    isEnd = true;
                }
            }
        }
    }
}
