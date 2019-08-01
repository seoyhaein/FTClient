package ai.icg.ftclient;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.Optional;

public class FTClient extends Application {
    // single javafx application
    //TODO Need to implement pid check. cuz when system shutdown lockfile still exist.
    private static boolean islock =  false;
    @Override
    public void start(Stage primaryStage) throws Exception {
        islock = lockInstance("lock.txt");
        if(!islock) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(null);
            alert.setContentText("program is running");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Platform.exit();
            }
        }

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginView.fxml"));
        primaryStage.setTitle("File Transfer Application");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    public static void main(String[] args){
        launch(args);
    }

    private static boolean lockInstance(final String lockFile) {
        try {
            final File file = new File(lockFile);
            final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            final FileLock fileLock = randomAccessFile.getChannel().tryLock();
            if (fileLock != null) {
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    public void run() {
                        try {
                            fileLock.release();
                            randomAccessFile.close();
                            file.delete();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
