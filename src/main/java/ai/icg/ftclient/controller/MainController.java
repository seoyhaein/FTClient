package ai.icg.ftclient.controller;

import ai.icg.ftclient.fc.*;
import javafx.event.ActionEvent;
import unlimited.fc.client.api.TransferMode;

import java.io.IOException;
import java.util.concurrent.*;

public class MainController {

    ExecutorService executorService = Executors.newFixedThreadPool(5);
    final ExecutorCompletionService<String> completionService = new ExecutorCompletionService<String>(executorService);
    LinkedBlockingQueue<FCTasks> taskLinkedBlockingQueue = new LinkedBlockingQueue<FCTasks>();

    private FCConfig fcConfig = populateconfigmodel();
    FC fc = new FC(fcConfig);

    public MainController() throws IOException {
    }

    FCTasks fcTask = new FCTasks("C:\\Temp\\seoy1.log");
    FCTasks fcTask1 = new FCTasks("C:\\Temp\\1.exe");
    FCTasks fcTask2 = new FCTasks("C:\\Temp\\2.exe");
    FCTasks fcTask3 = new FCTasks("C:\\Temp\\3.exe");
    FCTasks fcTask4 = new FCTasks("C:\\Temp\\seoy2.log");
    FCTasks fcTask5 = new FCTasks("C:\\Temp\\seoy3.log");

    public FCConfig populateconfigmodel() {
        FCConfig fccmodel = new FCConfig();

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

    /*public FileList populatefilelistA() {
        FileList fileMetaDataModel = new FileList();

        fileMetaDataModel.AddFileToList(fileMetaDataModel.new FileInfo("6.exe", "C:\\Temp\\6.exe"));
        fileMetaDataModel.AddFileToList(fileMetaDataModel.new FileInfo("7.exe", "C:\\Temp\\7.exe"));
        fileMetaDataModel.AddFileToList(fileMetaDataModel.new FileInfo("8.exe", "C:\\Temp\\8.exe"));
        fileMetaDataModel.AddFileToList(fileMetaDataModel.new FileInfo("9.exe", "C:\\Temp\\9.exe"));
        fileMetaDataModel.AddFileToList(fileMetaDataModel.new FileInfo("10.exe", "C:\\Temp\\10.exe"));

        return fileMetaDataModel;
    }*/

   // private FileList fileList = populatefilelistA();

    Future<String> future = null;

    public void OnTransfer(ActionEvent actionEvent) throws IOException, InterruptedException, ExecutionException {
        //TODO for test

        taskLinkedBlockingQueue.add(fcTask);
        taskLinkedBlockingQueue.add(fcTask1);
        taskLinkedBlockingQueue.add(fcTask2);
        int i = 0;

        // 최초실행 3번을 넘어가면 그때 풀에 넣어두기 시작함.
        completionService.submit(fcTask3, fcTask3.getFilepath());
        i++;
        completionService.submit(fcTask4, fcTask4.getFilepath());
        i++;
        completionService.submit(fcTask5, fcTask5.getFilepath());
        i++;
        //}

        // 등록한 task 의 갯수를 어떻게 동적으로 바꾸지??
        while (i > 0) {
            future = completionService.take();
            FCTasks task = taskLinkedBlockingQueue.poll();
            i--;
            // 모든 task 수행 완료
            if (task == null && future == null)
                break;
                // que 에 아직 남아있는 경우
            else if (task != null && future != null) {
                completionService.submit(task, task.getFilepath());
                System.out.println(future.get());
                i++;
            }
            // que 를 다 비운 경우
            else if (task == null && future != null) {
                System.out.println(future.get());
            }
        }

        System.out.println("end");

    }

    public void OnStart(ActionEvent actionEvent) throws ExecutionException, InterruptedException, IOException {
        FCTaskMonitor fcTaskMonitor = new FCTaskMonitor();
        fcTaskMonitor.RunforDebug();
        fcTaskMonitor.ShutDown();
    }
}
