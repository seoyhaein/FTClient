package ai.icg.ftclient.connection;

import java.sql.*;

//Singleton Class by seoy
public class ConnectionClass {
    private static Connection connection = null;
    private static PreparedStatement prepStatement = null;
    private static ResultSet resultSet = null;

    // TODO connectionString 암호화된 파일을 가져와서 설정해주는 방식으로 가야 클라이언트 업데이트가 편함.
    // ServerIp = JDBC_DB_URL, DB_NAME = DbName, JDBC_USER = UserName, JDBC_PASS = PassWord
    // String ServerIp, String DbName, String UserName, String PassWord

    private ConnectionClass() {
    }

    public static Connection getConnection(String JDBC_DRIVER, String JDBC_DB_URL, String DB_NAME, String JDBC_USER, String JDBC_PASS) throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        if (connection == null)
            connection = DriverManager.getConnection(JDBC_DB_URL + DB_NAME, JDBC_USER, JDBC_PASS);

        return connection;
    }

    public static void Close() throws SQLException {

        // Close Result Set Object
        if (resultSet != null) {
            resultSet.close();
            resultSet = null;
        }
        // Close Prepared Statement Object
        if (prepStatement != null) {
            prepStatement.close();
            prepStatement = null;
        }
        // Close Connection Object
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }
}
