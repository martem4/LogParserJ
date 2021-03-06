package Db;

import Model.LogFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DbLogSender {

//    private static final String JDBC_PROPERTIES_FILE="src/main/resources/jdbc.properties";
    private static final String JDBC_PROPERTIES_FILE="jdbc.properties";
    private static Properties propertiesDb = new Properties();
    private static InputStream inputStream;
    private static Connection connection;
    private static Statement statement;

    private static Properties readDbConProperties() throws IOException {
        inputStream = new FileInputStream(JDBC_PROPERTIES_FILE);
        propertiesDb.load(inputStream);
        return propertiesDb;
    }

    public static void sendLogToDb(String log, LogFile logFile) throws IOException {
        readDbConProperties();
        try {
                connection =  DriverManager.getConnection(propertiesDb.getProperty("url"),
                    propertiesDb.getProperty("login"),
                    propertiesDb.getProperty("password"));

            statement = connection.createStatement();
            String query = "INSERT INTO `systemevents` (`ReceivedAt`, `DeviceReportedTime`\n" +
                    " , `Facility`,`Priority`, `FromHost`, `Message`\n" +
                    " , `InfoUnitID`, `SysLogTag`, `EventLogType`,`GenericFileName`, `SystemID`, `processid`\n" +
                    " , `checksum`) VALUES (now(), now(), 2, 3, 'logserver', " +
                    "'" +log + "', 1., '" + logFile.getName() + "', NULL, NULL, NULL, '', 0);";
            statement.execute(query);

        }catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
