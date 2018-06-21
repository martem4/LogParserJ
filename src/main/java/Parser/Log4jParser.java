package Parser;

import Db.DbLogSender;
import Model.LogFile;

import java.util.regex.Pattern;

public class Log4jParser implements Parser {
    final String EXCEPTION_PATTERN = "ERROR|exception";
    final String NEWLINE_PATTERN = "^\\d\\d-\\d\\d-\\d\\d\\d\\d \\d\\d:\\d\\d:\\d\\d*";
    final Pattern exceptionPattern = Pattern.compile(EXCEPTION_PATTERN);
    final Pattern newLinePattern = Pattern.compile(NEWLINE_PATTERN);
    private DbLogSender dbLogSender;
    private LogFile logFile;
    private boolean isException = false;
    StringBuilder stringBuilder = new StringBuilder();


    public Log4jParser(DbLogSender dbLogSender, LogFile logFile) {
        super();
        this.dbLogSender = dbLogSender;
        this.logFile = logFile;
    }

    public void parse(String line) {
        if (isException) {
            if(newLinePattern.matcher(line).find()) {
                dbLogSender.sendLogToDb(stringBuilder.toString(), logFile);
                isException = false;
//                System.out.println(stringBuilder.toString());
//                System.out.println("################################################################################");
                stringBuilder.setLength(0);
            }
            else {
                stringBuilder.append(line+"\n");
            }
        }
        if (exceptionPattern.matcher(line).find()) {
            isException = true;
            stringBuilder.append(line+"\n");
        }
    }
}
