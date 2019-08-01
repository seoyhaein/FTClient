package ai.icg.ftclient.dao;

import ai.icg.ftclient.connection.ConnectionClass;
import ai.icg.ftclient.model.FTServerModel;
import ai.icg.ftclient.model.UserModel;

import java.sql.SQLException;

public class LoginDao {

    private String loginsql;

    public FTServerModel getServerIPnPort(UserModel userModel) throws SQLException, ClassNotFoundException {
        UserModel u = userModel;

        loginsql = "select userName,passWord,serverIP,serverPort from userT where userName =? AND passWord =?";

        FTServerModel ft = new FTServerModel();
        ConnectionClass connectionClass = new ConnectionClass();
        connectionClass.getConnection();
        connectionClass.getPrepStatement(loginsql);
        connectionClass.prepStatement.setString(1, u.getUserName());
        connectionClass.prepStatement.setString(2, u.getPassWord());

        connectionClass.resultSet = connectionClass.prepStatement.executeQuery();

        if (connectionClass.resultSet.first()) {
            ft.setServerIp(connectionClass.resultSet.getString("serverIp"));
            ft.setServerPort(connectionClass.resultSet.getInt("serverPort"));
            connectionClass.Close();
            return ft;
        } else {
            connectionClass.Close();
            return null;
        }

    }
}
