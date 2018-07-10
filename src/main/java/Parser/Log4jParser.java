package Parser;

import Db.DbLogSender;
import Model.LogFile;

import java.io.IOException;
import java.util.regex.Pattern;

public class Log4jParser implements Parser {
    final static String NEWLINE_PATTERN = "^.*\\d\\d:\\d\\d:\\d\\d.*";
    final static Pattern newLinePattern = Pattern.compile(NEWLINE_PATTERN);
    Pattern patternLogMode;

    private LogFile logFile;
    private boolean isException = false;
    StringBuilder stringBuilder = new StringBuilder();


    public Log4jParser(LogFile logFile) {
        super();
        this.logFile = logFile;
        patternLogMode = Pattern.compile(NEWLINE_PATTERN + logFile.getLogPatternMods());
    }

    public void parse(String line) {
        if (isException) {
            if(newLinePattern.matcher(line).find()) {
                try {
                    DbLogSender.sendLogToDb(stringBuilder.toString(), logFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                isException = false;
                prettyPrint(stringBuilder);
                stringBuilder.setLength(0);
            }
            else {
                stringBuilder.append(line+"\n");
            }
        }
        if (patternLogMode.matcher(line).find()) {
            isException = true;
            stringBuilder.append(line + "\n");
        }
    }

    private void prettyPrint(StringBuilder stringBuilder) {
        System.out.println(stringBuilder.toString());
        System.out.println("################################################################################");
    }
}
