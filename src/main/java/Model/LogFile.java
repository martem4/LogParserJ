package Model;

public class LogFile {

    public LogFile() {};
    public LogFile(String filePath, String type, String name) {
        super();
        this.filePath = filePath;
        this.type = type;
        this.name = name;
    }

    String filePath;
    String type;
    String name;

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

}
