package ai.icg.ftclient.controller;

import ai.icg.ftclient.dao.LoginDao;
import ai.icg.ftclient.model.FTServerModel;
import ai.icg.ftclient.model.UserModel;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class LoginController {
    public TextField txtID;
    public PasswordField txtPassWord;

    public void OnLogin(ActionEvent actionEvent) throws SQLException, ClassNotFoundException{
        UserModel u = new UserModel();
        LoginDao login = new LoginDao();
        FTServerModel ft;

        //TODO Textfield validation
        u.setUserName(txtID.getText());
        u.setPassWord(txtPassWord.getText());
        // if id and pwd does not exist it will return null
        ft = login.getServerIPnPort(u);
        if (ft != null) {
            //lbStatus.setText(ft.getServerIp());

            return;
        } else
            //lbStatus.setText("해당 아이디 또는 암호가 맞지 않습니다.");
        return;
    }
}
