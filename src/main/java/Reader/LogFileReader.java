package Reader;

import Model.LogFile;
import Parser.Parser;

import java.io.*;

public class LogFileReader <T extends Parser> extends Thread {

    private LogFile logFile;
    private Parser parser;
    private static final int FILE_READ_TIMEOUT = 5000;
    private boolean READ_FILE = true;

    public LogFileReader(final LogFile logFile, T parser) {
        this.logFile = logFile;
        this.parser = parser;
    }

    public void startReadFile() {
        String line;
        try {
            File f = new File(logFile.getFilePath());
            RandomAccessFile file = new RandomAccessFile(f, "r");
            file.seek(f.length());
            while(READ_FILE) {
                if (f.length() < file.length()) {
                    file = new RandomAccessFile(f, "r");
                    file.seek(f.length());
                }
                if((line = file.readLine()) != null) {
                    parser.parse(new String(line.getBytes("ISO-8859-1")).replace("'", ""));
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
        READ_FILE = false;
    }

    public void run() {
        startReadFile();
    }
}
