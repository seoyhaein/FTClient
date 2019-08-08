package ai.icg.ftclient.controller;

import ai.icg.ftclient.fc.FC;
import ai.icg.ftclient.model.FCConfigModel;
import ai.icg.ftclient.model.FileMetaDataModel;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.util.Pair;
import unlimited.fc.C.B;
import unlimited.fc.client.api.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.util.Callback;

public class MainController  {

    private static final int numofthreads = 2;
    private List<Task<Void>> taskList;
    //private FCConfigModel fccmodel = new FCConfigModel();
    //private FileMetaDataModel fileMetaDataModel = new FileMetaDataModel();

    private FileMetaDataModel populatefilelist(){
        FileMetaDataModel fileMetaDataModel = new FileMetaDataModel();

        fileMetaDataModel.FileList.add(fileMetaDataModel.new FileInfo("1.exe","C:\\Temp\\1.exe"));

        return fileMetaDataModel;
    }

    private FCConfigModel populateconfigmodel(){
        FCConfigModel fccmodel = new FCConfigModel();

        fccmodel.setIp("15.164.166.2");
        fccmodel.setPort(991);
        fccmodel.setShowConsoleLog(true);
        fccmodel.setAutoResume(true);
        fccmodel.setId("ichthys");
        fccmodel.setPwd("123");
        fccmodel.setTransferMode(TransferMode.AUTO);
        fccmodel.setStartRate(10000);
        fccmodel.setTargetRate(10000);
        fccmodel.setVerifyIntegrity(true);
        fccmodel.setAutoResume(true);

        return fccmodel;
    }

    private List<Task<Void>> CreateTaskList(int numofthread, FCConfigModel configmodel, FileMetaDataModel filemodel ){
        List<Task<Void>> list = new ArrayList<Task<Void>>();
        Task<Void> temp;

        for(int i = 0; i < numofthread; i++){
            temp = new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    FC fc = new FC();
                    Pair<UUID,FCClient> pair =  fc.CreateFCClient(configmodel);

                    fc.FileUpload(pair, filemodel);
                    fc.FcClose(pair.getValue());
                    return null;
                }
            };

            list.add(temp);
        }

        return null;
    }

    private void RunTaskList(List<Task<Void>> tasklist){
        ExecutorService executorService = Executors.newFixedThreadPool(numofthreads);
        tasklist.forEach( n -> {
            executorService.submit(n);
            executorService.shutdown();

        } );
    }

    public void OnTransfer(ActionEvent actionEvent) throws IOException {



    }

}
