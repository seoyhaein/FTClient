package ai.icg.ftclient.controller;

import ai.icg.ftclient.dao.LoginDao;
import ai.icg.ftclient.model.FTServerModel;
import ai.icg.ftclient.model.UserModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField txtID;
    public PasswordField txtPassWord;
    public JFXSpinner sp;
    public Label lbinfo;
    public JFXButton btnok;
    public JFXButton btnlogin;

    private Stage stage;

    private FTServerModel ft;
    private Task<Void> task;

    public void OnLogin(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        UserModel u = new UserModel();
        LoginDao login = new LoginDao();

        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        //TODO Textfield validation
        u.setUserName(txtID.getText());
        u.setPassWord(txtPassWord.getText());

        sp.setVisible(true);
        txtID.setVisible(false);
        txtPassWord.setVisible(false);
        btnlogin.setVisible(false);

        //TODO dbconnection test needed! by seoy
        // cuz only db connection try on task and db connection will be failed,
        // just throw connection fail exception. but this exception, javafx ui cannot catch it.

        task = new Task<Void>() {
            @Override
            public Void call() throws InterruptedException, SQLException, ClassNotFoundException {
                Thread.sleep(1000);
                ft = login.getServerIPnPort(u);

                return null;
            }

            @Override protected void succeeded() {
                super.succeeded();

                if (ft != null && stage != null) {
                    try {
                        Parent mainview = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));
                        Scene mainscen = new Scene(mainview);
                        stage.setScene(mainscen);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    lbinfo.setVisible(true);
                    lbinfo.setText("ID 또는 패스워드가 일치하지 않습니다.");
                    sp.setVisible(false);
                    btnlogin.setVisible(false);
                    btnok.setVisible(true);
                }

            }

            @Override protected void cancelled() {
                super.cancelled();
                updateMessage("Cancelled!");
            }

            @Override protected void failed() {
                super.failed();
                updateMessage("Failed!");
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sp.setVisible(false);
        lbinfo.setVisible(false);
        btnok.setVisible(false);
    }

    public void OnCheck(ActionEvent actionEvent) {
        lbinfo.setVisible(false);
        btnok.setVisible(false);

        txtID.setVisible(true);
        txtID.setText("");
        txtPassWord.setText("");

        txtPassWord.setVisible(true);
        btnlogin.setVisible(true);

    }

}


