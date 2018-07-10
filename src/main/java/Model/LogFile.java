package Model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "logFile")
public class LogFile {

    public LogFile() {}
    public LogFile(String filePath, String type, String name, List<String> logLevel) {
        super();
        this.filePath = filePath;
        this.type = type;
        this.name = name;
        this.logLevel = logLevel;
    }

    String filePath;
    String type;
    String name;
    List<String> logLevel;

    @XmlElementWrapper
    @XmlElement(name = "level")
    public List<String> getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(List<String> logLevel) {
        this.logLevel = logLevel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogPatternMods() {
        String modResult = "(";
        for (int i=0; i < this.logLevel.size(); i++) {
            modResult += this.logLevel.get(i);
            if (i != this.logLevel.size()-1) {
                modResult += "|";
            }
        }
        return modResult+")";
    }

}
