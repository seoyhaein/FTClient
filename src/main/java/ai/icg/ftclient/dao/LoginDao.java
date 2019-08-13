package ai.icg.ftclient.dao;

import ai.icg.ftclient.connection.ConnectionClass;
import ai.icg.ftclient.model.FTServerModel;
import ai.icg.ftclient.model.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao {

    private String loginsql;
    private PreparedStatement preparedStatement = null;
    private Connection connection = null;
    private ResultSet resultSet = null;

    // JDBC Driver Name & Database URL
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String JDBC_DB_URL = "jdbc:mysql://localhost/";
    private final String DB_NAME = "test1?characterEncoding=UTF-8&serverTimezone=UTC"; //Timezone setting

    // JDBC Database Credentials
    private final String JDBC_USER = "seoy";
    private final String JDBC_PASS = "dlrxntm123!";

    public FTServerModel getServerIPnPort(UserModel userModel) throws SQLException, ClassNotFoundException {

        UserModel u = userModel;

        loginsql = "select userName,passWord,serverIP,serverPort from userT where userName =? AND passWord =?";

        FTServerModel ft = new FTServerModel();

        connection = ConnectionClass.getConnection(JDBC_DRIVER, JDBC_DB_URL, DB_NAME, JDBC_USER, JDBC_PASS);
        preparedStatement = connection.prepareStatement(loginsql);
        preparedStatement.setString(1, u.getUserName());
        preparedStatement.setString(2, u.getPassWord());

        resultSet = preparedStatement.executeQuery();

        if (resultSet.first()) {
            ft.setServerIp(resultSet.getString("serverIp"));
            ft.setServerPort(resultSet.getInt("serverPort"));
            ConnectionClass.Close();
            return ft;
        } else {
            ConnectionClass.Close();
            return null;
        }

    }
}
