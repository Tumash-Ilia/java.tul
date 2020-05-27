package competition.filehandling;

import competition.app.Runner;
import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class Writer {
    public static File dataDirectory = new File(System.getProperty("user.dir") + File.separator + "data");
    
    public abstract void saveResults(String resultFilepath, List<Runner> runners) throws IOException;
}
