package ai.icg.ftclient.connection;

import java.sql.*;

public class ConnectionClass {
    private Connection connection;
    public PreparedStatement prepStatement;
    public ResultSet resultSet;

    // JDBC Driver Name & Database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    static final String JDBC_DB_URL = "jdbc:mysql://localhost/";
    static final String DB_NAME = "test1?characterEncoding=UTF-8&serverTimezone=UTC"; //Timezone setting


    // JDBC Database Credentials
    static final String JDBC_USER = "seoy";
    static final String JDBC_PASS = "dlrxntm123!";

    // TODO connectionString 암호화된 파일을 가져와서 설정해주는 방식으로 가야 클라이언트 업데이트가 편함.
    // ServerIp = JDBC_DB_URL, DB_NAME = DbName, JDBC_USER = UserName, JDBC_PASS = PassWord
    // String ServerIp, String DbName, String UserName, String PassWord
    public void getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(JDBC_DB_URL + DB_NAME, JDBC_USER, JDBC_PASS);
    }

    public void getPrepStatement(String sql) throws SQLException {
        if (connection != null) {
            prepStatement = connection.prepareStatement(sql);
        }
    }

    public void Close() throws SQLException {

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
