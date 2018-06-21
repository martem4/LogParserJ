package Reader;

import Model.LogFile;
import Parser.Parser;

import java.io.*;

public class LogFileReader <T extends Parser> {

    private LogFile logFile;
    private Parser parser;
    private static final int FILE_READ_TIMEOUT = 5000;

    public LogFileReader(final LogFile logFile, T parser) {
        this.logFile = logFile;
        this.parser = parser;
    }

    public void startReadFile() {
        String line;
        try {
            RandomAccessFile file = new RandomAccessFile(logFile.getFilePath(), "r");
            file.seek(file.length());
            while(true) {
                if((line = file.readLine()) != null) {
                    parser.parse(line);
                } else {
                    Thread.sleep(FILE_READ_TIMEOUT);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopReadFile() {
    }

}
