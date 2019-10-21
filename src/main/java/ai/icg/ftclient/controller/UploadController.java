package ai.icg.ftclient.controller;

import ai.icg.ftclient.FTClient;
import ai.icg.ftclient.fc.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.TaskProgressView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.concurrent.*;

public class UploadController  {

    public TaskProgressView tpv;
    public Queue<FileList> fileInfoQueue = null;

    public UploadController() throws IOException {
    }

    public void OnSearchFiles(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Multiple Files Dialog");

        //TODO DB 에서 가지고 와서 업로드할것들만 업로드한다.
        //Set extension filter
        FileChooser.ExtensionFilter extFilterExe =
                new FileChooser.ExtensionFilter("응용 프로그램(.exe)", "*.exe");
        //
        fileChooser.getExtensionFilters().addAll(
                extFilterExe
        );

        Stage stage =   FTClient.stage;
        List<File> files = fileChooser.showOpenMultipleDialog(stage);

        Queue<FileList> fileInfoQueue =  new LinkedList<FileList>();

        for(File m : files){

            FileList fileInfo = new FileList(m.getName(), m.getAbsolutePath());
            fileInfoQueue.add(fileInfo);
        }
        this.fileInfoQueue = fileInfoQueue;

    }

    public void OnExe(ActionEvent actionEvent) throws ExecutionException, InterruptedException {
        FCTaskMonitor fcTaskMonitor = new FCTaskMonitor();
        fcTaskMonitor.RunTask(this.fileInfoQueue);
        fcTaskMonitor.ShutDown();
    }

}
