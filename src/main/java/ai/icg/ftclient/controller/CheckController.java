package ai.icg.ftclient.controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CheckController implements Initializable {

    // id and pass does not match, loginfailure is true;
    private String check;
    private boolean loginfailure = true;
    private boolean portblock = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //new CheckProgress().start();
    }

    /*class CheckProgress extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<Window> wins = Stage.getWindows().filtered(window -> window.isShowing());
                        Stage stage = null;

                        for (Window win : wins) {
                            if (((Stage) win).getTitle().equals("File Transfer Application"))
                                stage = (Stage) win;
                        }
                        if (stage != null) {
                            //login fail
                            if (loginfailure) {
                                Parent loginview = FXMLLoader.load(getClass().getResource("/fxml/LoginView.fxml"));
                                Scene loginscen = new Scene(loginview);
                                stage.setScene(loginscen);
                            } else {
                                Parent mainview = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));
                                Scene mainscen = new Scene(mainview);
                                stage.setScene(mainscen);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }*/

    public String getCheck(String string) {
        check = string;
        return string;
    }

    public void setCheck(String string) {
        this.check = string;

    }
}
