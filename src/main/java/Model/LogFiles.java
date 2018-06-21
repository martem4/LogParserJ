package Model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "logFiles")
public class LogFiles {

    public LogFiles() {}

    public LogFiles(List<LogFile> logFile){
        super();
        this.logFile = logFile;
    }

    List<LogFile> logFile;

    @XmlElement
    public List<LogFile> getLogFile() {
        return logFile;
    }

    public void setLogFile(List<LogFile> logFile) {
        this.logFile = logFile;
    }


}
