import Db.DbLogSender;
import Model.LogFile;
import Model.LogFiles;
import Parser.Log4jParser;
import Reader.LogFileReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class LogParserJ {

//    private static final String LOGS_CONFIG_XML = "src/main/resources/logs.xml";
    private static final String LOGS_CONFIG_XML = "logs.xml";

    private List<LogFile> readLogsXml() {
        List<LogFile> logFileList = null;
        try {
            File logXml = new File(LOGS_CONFIG_XML);
            JAXBContext jaxbContext = JAXBContext.newInstance(LogFiles.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            logFileList = ((LogFiles)unmarshaller.unmarshal(logXml)).getLogFile();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return logFileList;
    }

    public static void main(String[] args) throws IOException {
        LogParserJ logParser = new LogParserJ();
        DbLogSender dbLogSender = new DbLogSender();
        dbLogSender.initDbConnection();
        for(LogFile logFile : logParser.readLogsXml()) {
            LogFileReader lfr = new LogFileReader(logFile, new Log4jParser(dbLogSender, logFile));
            lfr.start();
        }
    }
}
