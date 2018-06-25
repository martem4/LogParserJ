package Parser;

import Db.DbLogSender;
import Model.LogFile;

import java.io.IOException;
import java.util.regex.Pattern;

public class Log4jParser implements Parser {
    final String EXCEPTION_PATTERN = "ERROR|exception";
    final String NEWLINE_PATTERN = "^\\d\\d-\\d\\d-\\d\\d\\d\\d \\d\\d:\\d\\d:\\d\\d*";
    final Pattern exceptionPattern = Pattern.compile(EXCEPTION_PATTERN);
    final Pattern newLinePattern = Pattern.compile(NEWLINE_PATTERN);
    private LogFile logFile;
    private boolean isException = false;
    StringBuilder stringBuilder = new StringBuilder();


    public Log4jParser(LogFile logFile) {
        super();
        this.logFile = logFile;
    }

    public void parse(String line) {
        if (isException) {
            if(newLinePattern.matcher(line).find()) {
                try {
                    new DbLogSender().sendLogToDb(stringBuilder.toString(), logFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
